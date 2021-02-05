<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Registration.aspx.cs" Inherits="AppSecProject.Registration" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
    <script type ="text/javascript">
        function validate() {
            var str = document.getElementById('<%=tb_pwd.ClientID%>').value;

            if (str.length < 8) {
                document.getElementById("lbl_pwdchecker").innerHTML = "Password Length Must from 8-20 Characters Long!"
                document.getElementById("lbl_pwdchecker").style.color = "Red";
                return ("TooShort")
            }
            else if (str.length > 20) {
                document.getElementById("lbl_pwdchecker").innerHTML = "Password Length Must Be from 8-20 Characters Long!"
                document.getElementById("lbl_pwdchecker").style.color = "Red";
                return ("TooLong")

            }

            else if (str.search(/[0-9]/) == -1) {
                document.getElementById("lbl_pwdchecker").innerHTML = "Password Must Contain at Least 1 Number!"
                document.getElementById("lbl_pwdchecker").style.color = "Red";
                return ("NoNum")
            }
            else if (str.search(/[A-Z]/) == -1) {
                document.getElementById("lbl_pwdchecker").innerHTML = "Password Must Contain at Least 1 Uppercase Letter!"
                document.getElementById("lbl_pwdchecker").style.color = "Red";
                return ("NoUp")
            }
            else if (str.search(/[a-z]/) == -1) {
                document.getElementById("lbl_pwdchecker").innerHTML = "Password Must Contain at Least 1 Lowercase Letter!"
                document.getElementById("lbl_pwdchecker").style.color = "Red";
                return ("NoLow")
            }
            else if (str.search(/[^a-zA-Z0-9]/) == -1) {
                document.getElementById("lbl_pwdchecker").innerHTML = "Password Must Contain at Least 1 Special Character!"
                document.getElementById("lbl_pwdchecker").style.color = "Red";
                return ("NoSpecial")
            }
            document.getElementById("lbl_pwdchecker").innerHTML = "Excellent!"
            document.getElementById("lbl_pwdchecker").style.color = "Blue"
        }
        function validateSamePwd() {
            var p = document.getElementById('<%=tb_pwd.ClientID%>').value;
            var cfp = document.getElementById('<%=tb_cfpwd.ClientID%>').value;
            if (cfp != p) {
                document.getElementById("lbl_cfmpwd").innerHTML = "Passwords Must be The Same!"
                document.getElementById("lbl_cfmpwd").style.color = "Red"
                return("No Match")
            }
            document.getElementById("lbl_cfmpwd").innerHTML = "Excellent!"
            document.getElementById("lbl_cfmpwd").style.color = "Blue"
        }
        
        
        
    </script>
    <style type="text/css">
        .auto-style1 {
            height: 46px;
        }
        .auto-style3 {
            height: 46px;
            width: 116px;
        }
        .auto-style4 {
            width: 116px;
        }
    </style>
</head>
<body>
        <form id="form1" runat="server">
    <div >
    
    <h2>
        <br />
        <asp:Label ID="Label1" runat="server" Text="Account Registration"></asp:Label>
        <br />
        <br />
   </h2>
        <table class="style1">
            <tr>
                <td class="auto-style3">
        <asp:Label ID="Label2" runat="server" Text="Email "></asp:Label>
                </td>
                <td class="auto-style1">
                    <asp:TextBox ID="tb_userid" runat="server" Height="36px" Width="280px" TextMode="Email"></asp:TextBox>
            <asp:Label ID="lbl_email" runat="server"></asp:Label>
                </td>
            </tr>
            <tr>
                <td class="auto-style4">
        <asp:Label ID="Label3" runat="server" Text="Password"></asp:Label>
                </td>
                <td class="style2">
                    <asp:TextBox ID="tb_pwd" runat="server" Height="32px" Width="281px" onkeyup="javascript:validate()" TextMode="Password">mypassword</asp:TextBox>
            <asp:Label ID="lbl_pwdchecker" runat="server"></asp:Label>
                </td>
            </tr>
                        <tr>
                <td class="auto-style3">
        <asp:Label ID="Label4" runat="server" Text="Confirm Password"></asp:Label>
                </td>
                <td class="auto-style1">
                    <asp:TextBox ID="tb_cfpwd" runat="server" Height="32px" Width="281px" onkeyup="javascript:validateSamePwd()" TextMode="Password"></asp:TextBox>
                    <asp:Label ID="lbl_cfmpwd" runat="server"></asp:Label>
                </td>
            </tr>
                        <tr>
                <td class="auto-style4">
        <asp:Label ID="Label5" runat="server" Text="Credit Card"></asp:Label>
                </td>
                <td class="style7">
                    <asp:TextBox ID="tb_credit" runat="server" Height="32px" Width="281px" TextMode="Number"></asp:TextBox>
        <asp:Label ID="lbl_credit" runat="server"></asp:Label>
                </td>
            </tr>
                        <tr>
                <td class="auto-style3">
        <asp:Label ID="Label6" runat="server" Text="First Name"></asp:Label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </td>
                <td class="auto-style1">
                    <asp:TextBox ID="tb_fname" runat="server" Height="32px" Width="281px" ></asp:TextBox>
        <asp:Label ID="lbl_num" runat="server"></asp:Label>
                </td>
            </tr>
                        <tr>
                <td class="auto-style4">
       
                    <asp:Label ID="Label7" runat="server" Text="Last Name"></asp:Label>
       
                </td>
                <td class="style5">
                    <asp:TextBox ID="tb_lname" runat="server" Height="32px" Width="281px" ></asp:TextBox>
                    <br />
                </td>
            </tr>
            <tr>
                <td class="auto-style4">
       
                    <asp:Label ID="Label8" runat="server" Text=" Birthdate"></asp:Label>
       
                </td>
                <td class="style5">
                    <asp:TextBox ID="tb_birthdate" runat="server" Height="32px" Width="281px" ></asp:TextBox>
                    <br />
                </td>
            </tr>
    </table>
        
        
    <asp:Button ID="btn_Submit" runat="server" Height="48px"
        onclick="btn_Submit_Click" Text="Submit" Width="288px" />
        
        
    <asp:Button ID="btn_Login" runat="server" Height="48px"
        onclick="btnLogin_Submit_Click" Text="Login" Width="288px" />
<br />
        
        <asp:Label ID="lb_error1" runat="server"></asp:Label>
        <br />
        <asp:Label ID="lb_error2" runat="server"></asp:Label>
    <br />
        <asp:Label ID="lbl_empty" runat="server"></asp:Label>
        <br />
    
    </div>
    </form>
</body>
</html>
