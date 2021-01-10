package com.corootine.fuzzy.network.di

import android.content.Context
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import javax.net.ssl.*
import javax.net.ssl.TrustManagerFactory

class TrustManagerFactory constructor(context: Context) {

    companion object {
        private const val KEYSTORE_PASSWORD = "434706d2-b60c-4b2d-85e0-f889e92ce393"
        private const val CERTIFICATE_ASSET_FILE_NAME = "cert.pem"
    }

    val x509TrustManager: X509TrustManager
    val sslSocketFactory: SSLSocketFactory

    init {
        val trustedCertificates = loadTrustedCertificate(context)
        val keyStoreWithTrustedCertificates = createKeyStoreWithCertificate(trustedCertificates)

        x509TrustManager = createTrustManager(keyStoreWithTrustedCertificates)
        sslSocketFactory = createSslSocketFactory(x509TrustManager)
    }

    private fun loadTrustedCertificate(context: Context): Certificate {
        val inputStream = context.assets.open(CERTIFICATE_ASSET_FILE_NAME)
        val certificateFactory = CertificateFactory.getInstance("X.509")
        return certificateFactory.generateCertificate(inputStream)
    }

    private fun createKeyStoreWithCertificate(trustedCertificate: Certificate): KeyStore {
        val keyStore: KeyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(null, KEYSTORE_PASSWORD.toCharArray())
        keyStore.setCertificateEntry("certificate", trustedCertificate)
        return keyStore
    }

    private fun createTrustManager(keyStoreWithTrustedCertificate: KeyStore): X509TrustManager {
        val keyManagerFactory =
            KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
        keyManagerFactory.init(keyStoreWithTrustedCertificate, KEYSTORE_PASSWORD.toCharArray())
        val trustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keyStoreWithTrustedCertificate)

        return trustManagerFactory.trustManagers[0] as X509TrustManager
    }

    private fun createSslSocketFactory(trustManager: X509TrustManager): SSLSocketFactory {
        val sslContext: SSLContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
        return sslContext.socketFactory
    }
}