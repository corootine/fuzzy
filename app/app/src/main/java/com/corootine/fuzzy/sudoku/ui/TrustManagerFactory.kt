package com.corootine.fuzzy.sudoku.ui

import android.content.Context
import java.io.InputStream
import java.lang.String
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.util.*
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager
import kotlin.collections.ArrayList

object TrustManagerFactory {

    private const val KEYSTORE_PASSWORD = "434706d2-b60c-4b2d-85e0-f889e92ce393"
    private const val CERTIFICATE_ASSET_FILE_NAME = "cert.pem"

    fun create(context: Context): X509TrustManager {
        val trustedCertificates = loadTrustedCertificates(context)
        val keyStoreWithTrustedCertificates = createKeyStoreWithCertificates(trustedCertificates)
        return createTrustManager(keyStoreWithTrustedCertificates)
    }

    private fun loadTrustedCertificates(context: Context): List<Certificate> {
        val trustedCertificates: MutableList<Certificate> = ArrayList()
        val inputStream = context.assets.open(CERTIFICATE_ASSET_FILE_NAME)
        val trustedCertificate = createCertificate(inputStream)
        trustedCertificates.add(trustedCertificate)
        return trustedCertificates
    }

    private fun createCertificate(inputStream: InputStream): Certificate {
        val certificateFactory = CertificateFactory.getInstance("X.509")
        return certificateFactory.generateCertificate(inputStream)
    }

    private fun createKeyStoreWithCertificates(trustedCertificates: List<Certificate>): KeyStore {
        val keyStore: KeyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(null, KEYSTORE_PASSWORD.toCharArray())
        for (i in trustedCertificates.indices) {
            keyStore.setCertificateEntry(i.toString(), trustedCertificates[i])
        }
        return keyStore
    }

    private fun createTrustManager(keyStoreWithTrustedCertificates: KeyStore): X509TrustManager {
        val keyManagerFactory = KeyManagerFactory.getInstance(
            KeyManagerFactory.getDefaultAlgorithm()
        )
        keyManagerFactory.init(keyStoreWithTrustedCertificates, KEYSTORE_PASSWORD.toCharArray())
        val trustManagerFactory: TrustManagerFactory = TrustManagerFactory.getInstance(
            TrustManagerFactory.getDefaultAlgorithm()
        )
        trustManagerFactory.init(keyStoreWithTrustedCertificates)
        val trustManagers: Array<TrustManager> = trustManagerFactory.getTrustManagers()
        check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
            String.format(
                "Unexpected default trust managers: %s",
                Arrays.toString(trustManagers)
            )
        }
        return trustManagers[0] as X509TrustManager
    }
}