<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Success.aspx.cs" Inherits="Password_Hashing.Success" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <fieldset>
        <legend>User Profile</legend>
        <h2>User ID : <asp:Label ID="lbl_userID" runat="server"></asp:Label>
        </h2>
        <h2>Credit Card :&nbsp;
            <asp:Label ID="lbl_credit" runat="server"></asp:Label>
        </h2>
            <asp:Button ID="btnLogout" runat="server" Text="Logout" OnClick="LogoutMe"  />
            <asp:Button ID="btnChPass" runat="server" Text="Change Password" OnClick="ChPass"  />
    </fieldset>
    </div>
    </form>
</body>

</html>
