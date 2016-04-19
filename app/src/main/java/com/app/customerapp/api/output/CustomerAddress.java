package com.app.customerapp.api.output;

public class CustomerAddress {

    private String flatNumberHouseNumber;
    private String addressIdentifier;
    private String buildingSocietyStreet;
    private String areaLocality;
    private String city;
    private String state;
    private String country;
    private String pincode;

    /**
     * @return The flatNumberHouseNumber
     */
    public String getFlatNumberHouseNumber() {
        return flatNumberHouseNumber;
    }

    /**
     * @param flatNumberHouseNumber The flatNumberHouseNumber
     */
    public void setFlatNumberHouseNumber(String flatNumberHouseNumber) {
        this.flatNumberHouseNumber = flatNumberHouseNumber;
    }

    /**
     * @return The addressIdentifier
     */
    public String getAddressIdentifier() {
        return addressIdentifier;
    }

    /**
     * @param addressIdentifier The addressIdentifier
     */
    public void setAddressIdentifier(String addressIdentifier) {
        this.addressIdentifier = addressIdentifier;
    }

    /**
     * @return The buildingSocietyStreet
     */
    public String getBuildingSocietyStreet() {
        return buildingSocietyStreet;
    }

    /**
     * @param buildingSocietyStreet The buildingSocietyStreet
     */
    public void setBuildingSocietyStreet(String buildingSocietyStreet) {
        this.buildingSocietyStreet = buildingSocietyStreet;
    }

    /**
     * @return The areaLocality
     */
    public String getAreaLocality() {
        return areaLocality;
    }

    /**
     * @param areaLocality The areaLocality
     */
    public void setAreaLocality(String areaLocality) {
        this.areaLocality = areaLocality;
    }

    /**
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return The state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return The pincode
     */
    public String getPincode() {
        return pincode;
    }

    /**
     * @param pincode The pincode
     */
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }


}