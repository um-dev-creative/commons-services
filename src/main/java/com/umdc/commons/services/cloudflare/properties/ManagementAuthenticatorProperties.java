package com.umdc.commons.services.cloudflare.properties;

/**
 * Properties for management authenticator configuration.
 * This class holds the configuration properties for the management authenticator, including key alias, keystore, and truststore.
 */
public class ManagementAuthenticatorProperties {
    private String keyAlias;
    private StoreProperties keystore;
    private StoreProperties truststore;

    /**
     * Default constructor.
     * Creates a new instance of ManagementAuthenticatorProperties.
     */
    public ManagementAuthenticatorProperties() {
        // Default constructor
    }

    /**
     * Gets the key alias.
     *
     * @return the key alias
     */
    public String getKeyAlias() {
        return keyAlias;
    }

    /**
     * Sets the key alias.
     *
     * @param keyAlias the key alias to set
     */
    public void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }

    /**
     * Gets the keystore properties.
     *
     * @return the keystore properties
     */
    public StoreProperties getKeystore() {
        return keystore;
    }

    /**
     * Sets the keystore properties.
     *
     * @param keystore the keystore properties to set
     */
    public void setKeystore(StoreProperties keystore) {
        this.keystore = keystore;
    }

    /**
     * Gets the truststore properties.
     *
     * @return the truststore properties
     */
    public StoreProperties getTruststore() {
        return truststore;
    }

    /**
     * Sets the truststore properties.
     *
     * @param truststore the truststore properties to set
     */
    public void setTruststore(StoreProperties truststore) {
        this.truststore = truststore;
    }
}
