package com.umdc.commons.services.cloudflare.properties;

/**
 * Properties for keystore and truststore configuration.
 * This class holds the configuration properties for the keystore and truststore, including location, password, and type.
 */
public class StoreProperties {
    private String location;
    private String password;
    private String type;

    /**
     * Default constructor.
     * Creates a new instance of StoreProperties.
     */
    public StoreProperties() {
        // Default constructor
    }

    /**
     * Gets the location of the store.
     *
     * @return the location of the store
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the store.
     *
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the password for the store.
     *
     * @return the password for the store
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the store.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the type of the store.
     *
     * @return the type of the store
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the store.
     *
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
