package com.example.rohit.interviewapp.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Rohit on 1/15/2016.
 */
public class UserModel implements Serializable {

    private int id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String website;
    private Address address;
    private Company company;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    // private List<Address> addressList;
   // private List<company> companies;
    public UserModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return this.username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

//    public List<Address> getAddressList() {
//        return addressList;
//    }
//
//    public void setAddressList(List<Address> addressList) {
//        this.addressList = addressList;
//    }
//
//    public List<company> getCompanies() {
//        return companies;
//    }
//
//    public void setCompanies(List<company> companies) {
//        this.companies = companies;
//    }

    public class Address{
        private String street;
        private String suite;
        private String city;
        private String zipCode;
        private Geo geo;

        public Address(){
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getSuite() {
            return suite;
        }

        public void setSuite(String suite) {
            this.suite = suite;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public Geo getGeo() {
            return geo;
        }

        public void setGeo(Geo geo) {
            this.geo = geo;
        }


        public class Geo{
            private String lat;
            private String lng;

            public Geo(){
            }
            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }


        } // End of Geo
    } // End of Address

    public class Company{
        private String name;
        private String catchPhrase;
        private String bs;


        public Company(){
        }
        public String getCompanyName() {
            return name;
        }

        public void setCompanyName(String name) {
            this.name = name;
        }

        public String getCatchPhrase() {
            return catchPhrase;
        }

        public void setCatchPhrase(String catchPhrase) {
            this.catchPhrase = catchPhrase;
        }

        public String getBs() {
            return bs;
        }

        public void setBs(String bs) {
            this.bs = bs;
        }


    }
}
