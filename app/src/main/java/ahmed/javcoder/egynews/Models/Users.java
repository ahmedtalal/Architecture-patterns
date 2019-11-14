package ahmed.javcoder.egynews.Models;

public class Users  {

    private String username , email , phone , address  ,  image;

    public Users(String email, String username, String phone, String address , String imo)
    {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.image = imo ;
    }

    public Users(String email, String username)
    {
        this.username = username;
        this.email = email;
    }

    public Users()
    {

    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
