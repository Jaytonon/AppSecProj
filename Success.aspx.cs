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
using System.IO;

namespace AppSecProject
{
    public partial class Success : System.Web.UI.Page
    {
        string MYDBConnectionString = System.Configuration.ConfigurationManager.ConnectionStrings["MYDBConnection"].ConnectionString;
        static byte[] Key = null;
        static byte[] IV = null;
        static byte[] credit = null;
        static string userid = null;
        
        protected void Page_Load(object sender, EventArgs e)
           
        {
         

            if (Session["userID"] == null) {
                Response.Redirect("~/CustomError/HTTP403.htmL", false);
            }
            if (Session["userID"] != null && Session["AuthToken"] != null && Request.Cookies["AuthToken"] != null)
            {
                if (!Session["AuthToken"].ToString().Equals(Request.Cookies["AuthToken"].Value))
                {
                    Response.Redirect("Login.aspx", false);
                }
                else
                {
                    userid = Session["UserID"].ToString();
                    displayUserProfile(userid);
                }
            }
            
        }

        protected string decryptData(byte[] cipherText)
        {
            string plainText = null;

            try
            {
                RijndaelManaged cipher = new RijndaelManaged();
                cipher.IV = IV;
                cipher.Key = Key;
                // Create a decrytor to perform the stream transform.
                ICryptoTransform decryptTransform = cipher.CreateDecryptor();
                using (MemoryStream msDecrypt = new MemoryStream(cipherText))
                {
                    using (CryptoStream csDecrypt = new CryptoStream(msDecrypt, decryptTransform, CryptoStreamMode.Read))
                    {
                        using (StreamReader srDecrypt = new StreamReader(csDecrypt)) {
                            plainText = srDecrypt.ReadToEnd();
                        
                        }
                    }
                }
            }
            
            
            catch (Exception ex)
            {
                throw new Exception(ex.ToString());
            }
            finally { }
            return plainText;
        }
        protected void LogoutMe(object sender, EventArgs e )
        {
            Session.Clear();
            Session.Abandon();
            Session.RemoveAll();

            Response.Redirect("Login.aspx", false);

            if(Request.Cookies["ASP_NET_SessionId"] != null)
            {
                Response.Cookies["ASP_NET_SessionId"].Value = string.Empty;
                Response.Cookies["ASP_NET_SessionId"].Expires = DateTime.Now.AddMonths(-20);
            }
            if(Request.Cookies["AuthToken"] != null)
            {
                Response.Cookies["AuthToken"].Value = string.Empty;
                Response.Cookies["AuthToken"].Expires = DateTime.Now.AddMonths(-20);
            }
        }
        protected void ChPass(object sender, EventArgs e)
        {

            Response.Redirect("ChangePassword.aspx", false);

        }

        protected void displayUserProfile(string userid)
        {
            SqlConnection connection = new SqlConnection(MYDBConnectionString);
            string sql = "select * FROM ACCOUNT WHERE Email=@USERID";
            SqlCommand command = new SqlCommand(sql, connection);
            command.Parameters.AddWithValue("@USERID", userid);

            try
            {
                connection.Open();
                using (SqlDataReader reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        if (reader["Email"] != DBNull.Value)
                        {
                            lbl_userID.Text = reader["Email"].ToString();

                            //cipherTextCARD = (byte[])reader["Credit"];
                        }
                        if (reader["Credit"] != DBNull.Value)
                        {
                            credit = Convert.FromBase64String(reader["Credit"].ToString());

                            //cipherTextCARD = (byte[])reader["Credit"];
                        }
                        if (reader["IV"] != DBNull.Value)
                        {
                            IV = Convert.FromBase64String(reader["IV"].ToString());

                            //cipherTextCARD = (byte[])reader["Credit"];
                        }
                        if (reader["Key"] != DBNull.Value)
                        {
                            Key = Convert.FromBase64String(reader["Key"].ToString());

                            //cipherTextCARD = (byte[])reader["Credit"];
                        }
                    }
                        lbl_credit.Text = decryptData(credit);


                    
                    
                }

            }
            catch (Exception ex)
            {
                throw new Exception(ex.ToString());
            }

            finally
            {
                connection.Close();
            }
        }


    }

    
}