using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

using System.Security.Cryptography;
using System.Text;
using System.Data;
using System.Data.SqlClient;
using System.Net;
using System.IO;
using System.Web.Script.Serialization;
using System.Web.Services;
namespace Password_Hashing
{
    public partial class Login : System.Web.UI.Page
    {

        string MYDBConnectionString = System.Configuration.ConfigurationManager.ConnectionStrings["MYDBConnection"].ConnectionString;
        static string errorMsg = "";
        
        protected void Page_Load(object sender, EventArgs e)
        {
            lbl_error.Text = "";
        }
        public class MyObject
        {
            public string success { get; set; }
            public List<string> ErrorMessage { get; set; }
        }
        public bool ValidateCaptcha()
        {
            bool result = true;
            string captchaResponse = Request.Form["g-recaptcha-response"];
            HttpWebRequest req = (HttpWebRequest)WebRequest.Create(
                "https://www.google.com/recaptcha/api/siteverify?secret=6LcXs0YaAAAAAFYSXsKNKJw101rI6QphsITktZZA &response=" + captchaResponse);
            try
            {
                using (WebResponse wResponse = req.GetResponse())
                {
                    using (StreamReader readStream = new StreamReader(wResponse.GetResponseStream()))
                    {
                        string jsonResponse = readStream.ReadToEnd();
                        lbl_CapError.Text = "Please do the Captcha!";
                        lbl_CapError.ForeColor = System.Drawing.Color.Red;
                        JavaScriptSerializer js = new JavaScriptSerializer();
                        MyObject jsonObject = js.Deserialize<MyObject>(jsonResponse);
                        result = Convert.ToBoolean(jsonObject.success);
                        
                    }
                }
                return result;
            }
            catch (WebException ex)
            {
                throw ex;
            }
        }
        protected void btn_Submit_Click(object sender, EventArgs e)
        {
                     
            string pwd = tb_pwd.Text.ToString().Trim();
            string userid = tb_userid.Text.ToString().Trim();

            SHA512Managed hashing = new SHA512Managed();
            string dbHash = getDBHash(userid);
            string dbSalt = getDBSalt(userid);

            try
            {
                if (dbSalt != null && dbSalt.Length > 0 && dbHash != null && dbHash.Length > 0)
                {
                    string pwdWithSalt = pwd + dbSalt;
                    byte[] hashWithSalt = hashing.ComputeHash(Encoding.UTF8.GetBytes(pwdWithSalt));
                    string userHash = Convert.ToBase64String(hashWithSalt);
                    
                        if (userHash.Equals(dbHash))
                        {
                            if (ValidateCaptcha() == true)
                                {
                            
                                    if (LockCheck(userid)=="True")
                                    {
                                        lbl_error.Text = "Account Locked! Please try again later";
                                        lbl_error.ForeColor = System.Drawing.Color.Red;
                                    }
                                    else
                                    {
                                        Session["UserID"] = userid;
                                        
                                        string guid = Guid.NewGuid().ToString();
                                        Session["AuthToken"] = guid;
                                        Response.Cookies.Add(new HttpCookie("AuthToken", guid));
                                        Response.Redirect("Success.aspx", false);
                                    }
                                }
                        }
                        else
                        {
                        {
                            if (Session["LogInAttempt" + userid] == null)
                            {
                                Session["LogInAttempt" + userid] = 2;
                                int intAttempt = (int)Session["LogInAttempt" + userid];
                                
                            }

                            else
                            {
                                int intAttempt = (int)Session["LogInAttempt" + userid];
                                intAttempt -= 1;
                                Session["LogInAttempt" + userid] = intAttempt;
                                if (intAttempt < 0)
                                {
                                    SqlConnection connection = new SqlConnection(MYDBConnectionString);
                                    string sql = "UPDATE Account SET Lock = 1 WHERE Email=@Email";
                                    SqlCommand command = new SqlCommand(sql, connection);
                                    command.Parameters.AddWithValue("@Email", userid);
                                    try
                                    {
                                        connection.Open();
                                        SqlDataReader reader = command.ExecuteReader();
                                    }
                                    catch (Exception ex)
                                    {
                                        throw new Exception(ex.ToString());
                                    }
                                    finally { connection.Close(); }
                                    
                                }

                            }
                        }
                        lbl_error.Text = "Email or password is not valid. Please try again.";
                            lbl_error.ForeColor = System.Drawing.Color.Red;
                        }
                    
                }
            }
            catch (Exception ex)
            {
                throw new Exception(ex.ToString());
            }

            finally { }
            }
        


        protected string getDBSalt(string userid)
        {

            string s = null;

            SqlConnection connection = new SqlConnection(MYDBConnectionString);
            string sql = "select PASSWORDSALT FROM ACCOUNT WHERE Email=@USERID";
            SqlCommand command = new SqlCommand(sql, connection);
            command.Parameters.AddWithValue("@USERID", userid);

            try
            {
                connection.Open();

                using (SqlDataReader reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        if (reader["PASSWORDSALT"] != null)
                        {
                            if (reader["PASSWORDSALT"] != DBNull.Value)
                            {
                                s = reader["PASSWORDSALT"].ToString();
                            }
                        }
                    }
                }

            }
            catch (SqlException ex)
            {
                lbl_error.Text = "Invalid Search Input!";
            }

            finally { connection.Close(); }
            return s;

        }

        protected string getDBHash(string userid)
        {
            string h = "0";
            SqlConnection connection = new SqlConnection(MYDBConnectionString);
            string sql = "select PasswordHash FROM Account WHERE Email=@USERID";
            SqlCommand command = new SqlCommand(sql, connection);
            command.Parameters.AddWithValue("@USERID", userid);
            try
            {
                connection.Open();
                using (SqlDataReader reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        if (reader["PasswordHash"] != null)
                        {
                            if (reader["PasswordHash"] != DBNull.Value)
                            {
                                h = reader["PasswordHash"].ToString();
                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                throw new Exception(ex.ToString());
            }

            finally { connection.Close(); }
            return h;
        }

        protected string LockCheck(string Email)
        {
            string MYDBConnectionString = System.Configuration.ConfigurationManager.ConnectionStrings["MYDBConnection"].ConnectionString;
            string h = null;
            SqlConnection connection = new SqlConnection(MYDBConnectionString);
            string sql = "SELECT Lock FROM Account WHERE Email = @Email";
            SqlCommand command = new SqlCommand(sql, connection);
            command.Parameters.AddWithValue("@Email", Email);
            try
            {
                connection.Open();
                using (SqlDataReader reader = command.ExecuteReader())
                {

                    while (reader.Read())
                    {
                        if (reader["Lock"] != null)
                        {
                            if (reader["Lock"] != DBNull.Value)
                            {
                                h = reader["Lock"].ToString();
                            }
                        }
                    }

                }
            }
            catch (Exception ex)
            {
                throw new Exception(ex.ToString());
            }
            finally { connection.Close(); }
            return h;
        }



        protected string decryptData(byte[] cipherText)
        {
            string decryptedString = null;
            try
            {
                RijndaelManaged cipher = new RijndaelManaged();
                ICryptoTransform decryptTransform = cipher.CreateDecryptor();
            }
            catch (Exception ex)
            {
                throw new Exception(ex.ToString());
            }
            finally { }
            return decryptedString;
        }

        protected void btnRegister_Submit_Click(object sender, EventArgs e)
        {
            Response.Redirect("Registration.aspx");
        }

    }
}