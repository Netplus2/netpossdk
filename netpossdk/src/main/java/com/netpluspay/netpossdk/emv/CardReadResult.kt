package com.netpluspay.netpossdk.emv

import android.util.Log
import com.netpluspay.netpossdk.emv.data.EmvCard
import com.netpluspay.netpossdk.emv.data.TransactionData
import com.netpluspay.netpossdk.utils.HexDump
import com.netpluspay.netpossdk.utils.tlv.BerTag
import com.netpluspay.netpossdk.utils.tlv.BerTlvParser
import com.netpluspay.netpossdk.utils.tlv.HexUtil
import org.apache.commons.lang.StringUtils

class CardReadResult(
    private val readResultCode: Int,
    private val transactionData: TransactionData
) {
    var originalDeviceSerial: String? = null
        private set
    var applicationPrimaryAccountNumber: String? = null
        private set
    var cardHolderName: String? = null
        private set
    var track2Data: String? = null
        private set
    var applicationPANSequenceNumber: String? = null
        private set
    var cryptogram: String? = null
        private set
    var issuerApplicationData: String? = null
        private set
    var unpredictableNumber: String? = null
        private set
    var applicationTransactionCounter: String? = null
        private set
    var terminalVerificationResults: String? = null
        private set
    var transactionDate: String? = null
        private set
    var transactionType: String? = null
        private set
    var amount: String? = null
        private set
    var transactionCurrencyCode: String? = null
        private set
    var applicationInterchangeProfile: String? = null
        private set
    var terminalCountryCode: String? = null
        private set
    var cashBackAmount: String? = null
        private set
    var terminalCapabilities: String? = null
        private set
    var cardholderVerificationMethod: String? = "420300"
        private set
    var terminalType: String? = null
        private set
    var deviceSerialNumber: String? = null
        private set
    var authorizationResponseCode: String? = null
        private set
    var applicationVersionNumber: String? = null
        private set
    var transactionSequenceNumber: String? = null
        private set
    var expirationDate: String? = null
        private set
    var cardScheme: String? = null
        private set
    var cryptogramInformationData: String? = null
        private set
    var dedicatedFileName: String? = null
        private set
    var applicationDiscretionaryData: String? = null
        private set
    private var formFactorIndicator: String? = null
    var cardSequenceNumber: String? = null
        private set

    var encryptedPinBlock: String? = null

    fun getWithTag(tag: String): String? {
        return try {
            val tlvList = BerTlvParser().parse(transactionData.transData)
            tlvList.find(BerTag(tag)).hexValue
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun extractValues() {
        this.cardScheme = EmvCard(transactionData.transData).type.getName()
        val mTlvParser = BerTlvParser()
        val data = transactionData.transData
        val mTlvs = mTlvParser.parse(data)
        for (tlv in mTlvs.list) {
            // Log.e("TAG", "Emv tag: ${tlv.tag.berTagHex}  -  value ${tlv.hexValue}")
            when (tlv.tag.berTagHex) {
                "5A" -> applicationPrimaryAccountNumber = tlv.hexValue
                "5F20" -> cardHolderName = tlv.textValue
                "57" -> track2Data = tlv.hexValue
                "5F34" -> {
                    cardSequenceNumber = tlv.hexValue
                    applicationPANSequenceNumber = StringUtils.leftPad(tlv.hexValue, 3, '0')
                }
                "9F27" -> cryptogramInformationData = tlv.hexValue
                "9F37" -> unpredictableNumber = tlv.hexValue
                "9F36" -> applicationTransactionCounter = tlv.hexValue
                "95" -> terminalVerificationResults = tlv.hexValue
                "9A" -> transactionDate = tlv.hexValue
                "9C" -> transactionType = tlv.hexValue
                "9F02" -> amount = tlv.hexValue
                "5F24" -> expirationDate = tlv.hexValue
                "5F2A" -> transactionCurrencyCode = tlv.hexValue
                "82" -> applicationInterchangeProfile = tlv.hexValue
                "9F1A" -> terminalCountryCode = tlv.hexValue
                "9F03" -> cashBackAmount = tlv.hexValue
                "9F33" -> terminalCapabilities = tlv.hexValue
                "9F34" -> cardholderVerificationMethod = tlv.hexValue
                "9F35" -> terminalType = tlv.hexValue
                "9F1E" -> {
                    originalDeviceSerial = tlv.hexValue
                    deviceSerialNumber =
                        if (tlv.hexValue == "0000000000000000") "4231373931453158" else tlv.hexValue
                }
                "8A" -> authorizationResponseCode = tlv.hexValue
                "84" -> dedicatedFileName = tlv.hexValue
                "9F09" -> applicationVersionNumber = tlv.hexValue
                "9F41" -> transactionSequenceNumber = tlv.hexValue
                "9F26" -> cryptogram = tlv.hexValue
                "9F10" -> issuerApplicationData = tlv.hexValue
                "9F6E" -> formFactorIndicator = tlv.hexValue
                "9F05" -> applicationDiscretionaryData = tlv.hexValue
            }
        }
        Log.e("TAG", "Extract values")
    }

    val iccData = transactionData.transData

    val iccDataString = HexUtil.toHexString(transactionData.transData)

    val nibssIccSubset: String
        get() {
            val builder = java.lang.StringBuilder()
            builder.append("9F26")
                .append(HexDump.toHexString(("${cryptogram!!.length / 2}").toByte()))
                .append(
                    cryptogram
                )
                .append("9F27")
                .append(HexDump.toHexString(("${cryptogramInformationData!!.length / 2}").toByte()))
                .append(
                    cryptogramInformationData
                )
                .append("9F10")
                .append(HexDump.toHexString(("${issuerApplicationData!!.length / 2}").toByte()))
                .append(
                    issuerApplicationData
                )
                .append("9F37")
                .append(HexDump.toHexString(("${unpredictableNumber!!.length / 2}").toByte()))
                .append(
                    unpredictableNumber
                )
                .append("9F36")
                .append(HexDump.toHexString(("${applicationTransactionCounter!!.length / 2}").toByte()))
                .append(
                    applicationTransactionCounter
                )
                .append("95")
                .append(HexDump.toHexString(("${terminalVerificationResults!!.length / 2}").toByte()))
                .append(
                    terminalVerificationResults
                )
                .append("9A")
                .append(HexDump.toHexString(("${transactionDate!!.length / 2}").toByte()))
                .append(
                    transactionDate
                )
                .append("9C")
                .append(HexDump.toHexString(("${transactionType!!.length / 2}").toByte()))
                .append(
                    transactionType
                )
                .append("9F02").append(HexDump.toHexString(("${amount!!.length / 2}").toByte()))
                .append(amount)
                .append("5F2A")
                .append(HexDump.toHexString(("${transactionCurrencyCode!!.length / 2}").toByte()))
                .append(
                    transactionCurrencyCode
                )
                .append("82")
                .append(HexDump.toHexString(("${applicationInterchangeProfile!!.length / 2}").toByte()))
                .append(
                    applicationInterchangeProfile
                )
                .append("9F1A")
                .append(HexDump.toHexString(("${terminalCountryCode!!.length / 2}").toByte()))
                .append(
                    terminalCountryCode
                )
                .append("9F34")
                .append(HexDump.toHexString(("${cardholderVerificationMethod!!.length / 2}").toByte()))
                .append(
                    cardholderVerificationMethod
                )
                .append("9F33")
                .append(HexDump.toHexString(("${terminalCapabilities!!.length / 2}").toByte()))
                .append(
                    terminalCapabilities
                )
                .append("9F35")
                .append(HexDump.toHexString(("${terminalType!!.length / 2}").toByte()))
                .append(
                    terminalType
                )
                .append("9F1E")
                .append(HexDump.toHexString(("${deviceSerialNumber!!.length / 2}").toByte()))
                .append(
                    deviceSerialNumber
                )
                .append("84")
                .append(HexDump.toHexString(("${dedicatedFileName!!.length / 2}").toByte()))
                .append(
                    dedicatedFileName
                )
                .append("9F09")
                .append(HexDump.toHexString(("${applicationVersionNumber!!.length / 2}").toByte()))
                .append(
                    applicationVersionNumber
                )
                .append("9F03")
                .append(HexDump.toHexString(("${cashBackAmount!!.length / 2}").toByte()))
                .append(
                    cashBackAmount
                )
                .append("5F34")
                .append(HexDump.toHexString(("${cardSequenceNumber!!.length / 2}").toByte()))
                .append(
                    cardSequenceNumber
                )
            // .append("91").append(this.issueAuthenticationData);
            // .append("9F6E").append(this.formFactorIndicator);
            return builder.toString()
        }

    override fun toString(): String {
        return "CardReadResult{" +
            "cardResultCode=" + readResultCode +
            ", applicationPrimaryAccountNumber='" + applicationPrimaryAccountNumber + '\'' +
            ", cardHolderName='" + cardHolderName + '\'' +
            ", track2Data='" + track2Data + '\'' +
            ", applicationPANSequenceNumber='" + applicationPANSequenceNumber + '\'' +
            ", cryptogram='" + cryptogram + '\'' +
            ", issuerApplicationData='" + issuerApplicationData + '\'' +
            ", unpredictableNumber='" + unpredictableNumber + '\'' +
            ", applicationTransactionCounter='" + applicationTransactionCounter + '\'' +
            ", terminalVerificationResults='" + terminalVerificationResults + '\'' +
            ", transactionDate='" + transactionDate + '\'' +
            ", transactionType='" + transactionType + '\'' +
            ", amount='" + amount + '\'' +
            ", transactionCurrencyCode='" + transactionCurrencyCode + '\'' +
            ", applicationInterchangeProfile='" + applicationInterchangeProfile + '\'' +
            ", terminalCountryCode='" + terminalCountryCode + '\'' +
            ", cashBackAmount='" + cashBackAmount + '\'' +
            ", terminalCapabilities='" + terminalCapabilities + '\'' +
            ", cardholderVerificationMethod='" + cardholderVerificationMethod + '\'' +
            ", terminalType='" + terminalType + '\'' +
            ", originalDeviceSerial='" + originalDeviceSerial + '\'' +
            ", deviceSerialNumber='" + deviceSerialNumber + '\'' +
            ", authorizationResponseCode='" + authorizationResponseCode + '\'' +
            ", applicationVersionNumber='" + applicationVersionNumber + '\'' +
            ", transactionSequenceNumber='" + transactionSequenceNumber + '\'' +
            ", dedicatedFileName='" + dedicatedFileName + '\'' +
            ", issuerApplicationDiscretionaryData='" + applicationDiscretionaryData + '\'' +
            ", expirationDate='" + expirationDate + '\'' +
            '}'
    }

    // return cardResultCode == EmvApi.EMV_TRANS_ACCEPT || cardResultCode == EmvApi.EMV_TRANS_QPBOC_ACCEPT;
    val isEMVAccepted: Boolean
        get() = true

    // return cardResultCode == EmvApi.EMV_TRANS_ACCEPT || cardResultCode == EmvApi.EMV_TRANS_QPBOC_ACCEPT;
    init {
        extractValues()
    }
}
