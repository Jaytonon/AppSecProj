<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="ChangePassword.aspx.cs" Inherits="Password_Hashing.ChangePassword" %>

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
</head>
<body>
    <form id="form1" runat="server">
        <div>

            <table class="style1">
            <tr>
                <td class="style3">
        <asp:Label ID="Label2" runat="server" Text="Please Enter Password"></asp:Label>
                </td>
                <td class="style2">
                    <asp:TextBox ID="tb_pwd" runat="server" Height="32px" Width="281px" onkeyup="javascript:validate()" TextMode="Password">mypassword</asp:TextBox>

                    <asp:Label ID="lbl_pwdchecker" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td class="style3">
        <asp:Label ID="Label3" runat="server" Text="Re Enter Password"></asp:Label>
                </td>
                <td class="style2">
                    <asp:TextBox ID="tb_cfpwd" runat="server" Height="32px" Width="281px" onkeyup="javascript:validateSamePwd()" TextMode="Password"></asp:TextBox>
                    <asp:Label ID="lbl_cfmpwd" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            </table>
        <p>
            <asp:Button ID="Btn_ChPass" runat="server" OnClick="Btn_ChPass_Click" Text="Change Password" />
        </p>

        </div>
    </form>
</body>
</html>
