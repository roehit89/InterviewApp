package com.example.rohit.interviewapp;

/**
 * Created by Rohit on 1/15/2016.
 */
public class UserModel {

    private int userId;
    private String name;
    private String userName;
    private String email;
    private String phone;
    private String website;

    UserModel() {
    }

    public class Address{
        private String street;
        private String suite;
        private String city;
        private String zipCode;

        Address(){

        }

        public class Geo{
            private String lat;
            private String lng;

            Geo(){
            }
        } // End of Geo
    } // End of Address

    public class company{
        private String companyName;
        private String catchPhrase;
        private String bs;

        company(){
        }
    }
}
