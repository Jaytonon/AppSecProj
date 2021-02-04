<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Login.aspx.cs" Inherits="Password_Hashing.Login" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
    <script src="https://www.google.com/recaptcha/api.js"></script>
</head>
<body>
       <form id="form1" runat="server">
    <h2>
        <br />
        <asp:Label ID="Label1" runat="server" Text="Login"></asp:Label>
        <br />
        <br />
   </h2>
        <table class="style1">
            <tr>
                <td class="style3">
        <asp:Label ID="Label2" runat="server" Text="User ID/Email"></asp:Label>
                </td>
                <td class="style2">
                    <asp:TextBox ID="tb_userid" runat="server" Height="16px" Width="280px"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="style3">
        <asp:Label ID="Label3" runat="server" Text="Password"></asp:Label>
                </td>
                <td class="style2">
                    <asp:TextBox ID="tb_pwd" runat="server" Height="16px" Width="281px" TextMode="Password"></asp:TextBox>
                </td>
            </tr>
                        <tr>
                <td class="style3">
       
    <asp:Button ID="btn_Submit" runat="server" Height="48px" 
        onclick="btn_Submit_Click" Text="Submit" Width="288px" />
       
                </td>
                <td class="style2">
    <asp:Button ID="btn_Registration" runat="server" Height="48px" 
        onclick="btnRegister_Submit_Click" Text="Register" Width="288px" />
                </td>
            </tr>
    </table>
         <asp:Label ID="lbl_error" runat="server">      1234</asp:Label>
         <div class="g-recaptcha" data-sitekey="6LcXs0YaAAAAAHCXmuXixLWKZUVWBS6PrV2rrgqU"></div>
        <asp:Label ID="lbl_CapError" runat="server"></asp:Label>
&nbsp;&nbsp;&nbsp;
    <br />
           <br />
        
           <br />
           <br />
           <br />
        <br />
        <br />
   
    <div>
    
    </div>
    </form>
</body>
</html>
