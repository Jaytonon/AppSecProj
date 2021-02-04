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
using System.Text.RegularExpressions;
using System.Drawing;
using System.Net;
using System.IO;
using System.Web.Script.Serialization;
using System.Web.Services;

namespace Password_Hashing
{
    public partial class Registration : System.Web.UI.Page
    {   

        string MYDBConnectionString = System.Configuration.ConfigurationManager.ConnectionStrings["MYDBConnection"].ConnectionString;
        static string finalHash;
        static string salt;
        byte[] Key;
        byte[] IV;

        static string line = "\r";

        //static string isDebug = ConfigurationManager.AppSettings["isDebug"].ToString();

       
        protected void Page_Load(object sender, EventArgs e)
        {

        }
        private int checkPassword(string password)
        {
            int score = 0;

            if (password.Length < 8)
            {
                return 1;
            }
            else
            {
                score = 1;
            }
            if (Regex.IsMatch(password, "[a-z]"))
            {
                score++;
            }
            if (Regex.IsMatch(password, "[A-Z]"))
            {
                score++;
            }
            if (Regex.IsMatch(password, "[0-9]"))
            {
                score++;
            }
            if (Regex.IsMatch(password, "[^a-zA-Z0-9]"))
            {
                score++;
            }
            return score;
        }
        private int checkblank(string email,string fname,string lname,string credit)
        {
            int score = 1;

           
            if (Regex.IsMatch(email, "[a-zA-Z0-9]"))
            {
                score = 0 ;
            }
            if (Regex.IsMatch(fname, "[a-zA-Z0-9]"))
            {
                score = 0;
            }
            if (Regex.IsMatch(lname, "[a-zA-Z0-9]"))
            {
                score = 0;
            }
            if (Regex.IsMatch(credit, "[a-zA-Z0-9]"))
            {
                score = 0;
            }
            else
            {
                score = 1;
            }
            
            return score;
        }


        protected void btn_Submit_Click(object sender, EventArgs e)
        {
            tb_pwd.Text = HttpUtility.HtmlEncode(tb_pwd.Text);
            tb_userid.Text = HttpUtility.HtmlEncode(tb_userid.Text);
            tb_fname.Text = HttpUtility.HtmlEncode(tb_fname.Text);
            tb_lname.Text = HttpUtility.HtmlEncode(tb_lname.Text);
            tb_credit.Text = HttpUtility.HtmlEncode(tb_credit.Text);
            int score2 = checkblank(tb_userid.Text, tb_fname.Text,tb_lname.Text, tb_credit.Text);


                if (score2 == 0)
                {
                    



                        int scores = checkPassword(tb_pwd.Text);
                        string status = "";
                        switch (scores)
                        {
                            case 1:
                                status = "Very Weak";
                                break;
                            case 2:
                                status = "Weak";
                                break;
                            case 3:
                                status = "Medium";
                                break;
                            case 4:
                                status = "Strong";
                                break;
                            case 5:
                                status = "Excellent";
                                break;
                            default:
                                break;
                        }
                        lbl_pwdchecker.Text = "Status : " + status;
                        if (scores < 4)
                        {
                            lbl_pwdchecker.ForeColor = Color.Red;
                            return;
                        }
                        lbl_pwdchecker.ForeColor = Color.Green;
                        string pwd = tb_pwd.Text.ToString().Trim();
                       
                        //Generate random "salt" 
                        RNGCryptoServiceProvider rng = new RNGCryptoServiceProvider();
                        byte[] saltByte = new byte[8];

                        //Fills array of bytes with a cryptographically strong sequence of random values.
                        rng.GetBytes(saltByte);
                        salt = Convert.ToBase64String(saltByte);

                        SHA512Managed hashing = new SHA512Managed();

                        string pwdWithSalt = pwd + salt;
                        byte[] plainHash = hashing.ComputeHash(Encoding.UTF8.GetBytes(pwd));
                        byte[] hashWithSalt = hashing.ComputeHash(Encoding.UTF8.GetBytes(pwdWithSalt));

                        finalHash = Convert.ToBase64String(hashWithSalt);

                        lb_error1.Text = "Salt:" + salt;
                        lb_error2.Text = "Hash with salt:" + finalHash;

                        RijndaelManaged cipher = new RijndaelManaged();
                        cipher.GenerateKey();
                        Key = cipher.Key;
                        IV = cipher.IV;


                        createAccount();
                        Response.Redirect("Login.aspx");
     
                }
                else
                {
                    string stats = "No Blanks Allowed!";
                    lbl_empty.Text = stats;
                    lbl_empty.ForeColor = System.Drawing.Color.Red;
                }

            }
        


        protected void createAccount()
        {
            

            try
            {
                using (SqlConnection con = new SqlConnection(MYDBConnectionString))
                {
                    using (SqlCommand cmd = new SqlCommand("INSERT INTO Account VALUES(@Email, @Fname,@Lname,@Credit,@PasswordHash,@PasswordSalt,@DateTimeRegistered,@IV,@Key,@Lock)"))
                    {
                        
                            
                            using (SqlDataAdapter sda = new SqlDataAdapter())
                            {
                                cmd.CommandType = CommandType.Text;
                                cmd.Parameters.AddWithValue("@Email", tb_userid.Text.Trim());
                                cmd.Parameters.AddWithValue("@Fname", tb_fname.Text.Trim());
                                cmd.Parameters.AddWithValue("@Lname", tb_lname.Text.Trim());
                                cmd.Parameters.AddWithValue("@Credit", Convert.ToBase64String(encryptData(tb_credit.Text.Trim())));
                                cmd.Parameters.AddWithValue("@PasswordHash", finalHash);
                                cmd.Parameters.AddWithValue("@PasswordSalt", salt);
                                cmd.Parameters.AddWithValue("@DateTimeRegistered", DateTime.Now);
                                cmd.Parameters.AddWithValue("@IV", Convert.ToBase64String(IV));
                                cmd.Parameters.AddWithValue("@Key", Convert.ToBase64String(Key));
                                cmd.Parameters.AddWithValue("@Lock", 0);
                            cmd.Connection = con;
                                con.Open();
                                cmd.ExecuteNonQuery();
                                con.Close();
                            }
                        
                    }

                }
            }
            catch (Exception ex)
            {
                throw new Exception(ex.ToString());
            }
        }

        protected byte[] encryptData(string data)
        {
            byte[] cipherText = null;
            try
            {
                RijndaelManaged cipher = new RijndaelManaged();
                cipher.IV = IV;
                cipher.Key = Key;
                ICryptoTransform encryptTransform = cipher.CreateEncryptor();
                //ICryptoTransform decryptTransform = cipher.CreateDecryptor();
                byte[] plainText = Encoding.UTF8.GetBytes(data);
                cipherText = encryptTransform.TransformFinalBlock(plainText, 0, plainText.Length);


                //Encrypt
                //cipherText = encryptTransform.TransformFinalBlock(plainText, 0, plainText.Length);
                //cipherString = Convert.ToBase64String(cipherText);
                //Console.WriteLine("Encrypted Text: " + cipherString);

            }
            catch (Exception ex)
            {
                throw new Exception(ex.ToString());
            }

            finally { }
            return cipherText;
        }

        protected void btnLogin_Submit_Click(object sender, EventArgs e)
        {
            Response.Redirect("Login.aspx");
        }
    }
}