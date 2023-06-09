package com.netpluspay.netpossdk.core;

import android.graphics.Bitmap;

/**
 * @param <T> The ReceiptBuilder class which extends this class
 * @param <K> The return value of the final print function
 */
public abstract class AndroidTerminalReceiptBuilderFactory<T, K> {
    protected String title;
    protected String merchantAddress;
    protected String merchantName;
    protected String terminalId;
    protected String transactionType;
    protected String stan;
    protected String dateTime;
    protected String amount;
    protected String cardScheme;
    protected String cardNumber;
    protected String cardHolderName;
    protected String authorizationCode;
    protected String transactionStatus;
    protected String responseCode;
    protected String aid;
    protected String rrn;
    protected String description;
    protected String appName;
    protected String appVersion;
    protected String receiptCopy;
    protected String remark;
    protected boolean isReprint;
    protected String mid;
    protected String footNote;
    private final StringBuilder builder = new StringBuilder();

    abstract protected T getThis();

    public StringBuilder getBuilder() {
        return builder;
    }

    public T appendDescription(String description) {
        this.description = description;
        return getThis();
    }

    public T setIsReprint(boolean isReprint) {
        this.isReprint = isReprint;
        return getThis();
    }

    public T appendTitle(String title) {
        this.title = title;
        return getThis();
    }

    public T appendMid(String mid) {
        this.mid = mid;
        return getThis();
    }

    public T appendRemark(String remark) {
        this.remark = remark;
        return getThis();
    }

    public T appendMerchantName(String merchantName) {
        this.merchantName = merchantName;
        return getThis();
    }

    public T appendMerchantAddress(String addressOfMerchant) {
        this.merchantAddress = addressOfMerchant;
        return getThis();
    }

    public T appendTerminalId(String terminalId) {
        this.terminalId = terminalId;
        return getThis();
    }

    public T appendTransactionType(String transactionType) {
        this.transactionType = transactionType;
        return getThis();
    }

    public T appendStan(String stan) {
        this.stan = stan;
        return getThis();
    }

    public T appendDateTime(String dateTime) {
        this.dateTime = dateTime;
        return getThis();
    }

    public T appendAmount(String amount) {
        this.amount = amount;
        return getThis();
    }

    public T appendCardScheme(String cardScheme) {
        this.cardScheme = cardScheme;
        return getThis();
    }

    public T appendCardNumber(String number) {
        this.cardNumber = number;
        return getThis();
    }

    public T appendCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
        return getThis();
    }

    public T appendAuthorizationCode(String authCode) {
        this.authorizationCode = authCode;
        return getThis();
    }

    public T appendTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
        return getThis();
    }

    public T appendResponseCode(String responseCode) {
        this.responseCode = responseCode;
        return getThis();
    }

    public T appendAID(String aid) {
        this.aid = aid;
        return getThis();
    }

    public T appendRRN(String rrn) {
        this.rrn = rrn;
        return getThis();
    }

    public T appendAppName(String appName) {
        this.appName = appName;
        return getThis();
    }

    public T appendAppVersion(String appVersion) {
        this.appVersion = appVersion;
        return getThis();
    }

    public T isCustomerCopy() {
        this.receiptCopy = "**** CUSTOMER COPY ****";
        return getThis();
    }

    public T isMerchantCopy() {
        this.receiptCopy = "**** MERCHANT COPY ****";
        return getThis();
    }

    public T appendFootNote(String footNote){
        this.footNote = footNote;
        return getThis();
    }

    public abstract void appendLogo(Bitmap bitmap);

    protected void printLine() {
        appendTextEntity("\n");
        appendTextEntity("-------------------------------");
        appendTextEntity("\n");
    }

    public T buildString() {
        if (terminalId != null)
            builder.append("TERMINAL ID: ").append(terminalId).append("\n");
        if (transactionType != null)
            builder.append(transactionType).append("\n");
        if (dateTime != null)
            builder.append("DATE/TIME: ").append(dateTime).append("\n");
        if (amount != null)
            builder.append("AMOUNT: ").append(amount).append("\n");
        if (cardScheme != null)
            builder.append(cardScheme).append(" Ending with").append(cardNumber.substring(cardNumber.length() - 4)).append("\n");
        if (responseCode != null)
            builder.append("RESPONSE CODE: ").append(responseCode).append("\n");

        return getThis();
    }


    public T build() {
        if (title != null)
            appendTextEntity(title);

        if (merchantName != null)
            appendTextEntityLargeFontCenter(merchantName);

        if (merchantAddress != null) {
            appendTextEntityCenter("\n" + merchantAddress);
        } else {
            appendTextEntityCenter("\n No Address found for user");
        }

        if (terminalId != null) {
            appendTextEntity("\nTERMINAL ID: " + terminalId);
        }

        if (mid != null) {
            appendTextEntity("MID: " + mid);
        }

        if (transactionType != null) {
            appendTextEntityFontSixteenCenter(transactionType);
        }

        if (stan != null)
            appendTextEntityBold("STAN: " + stan);

        if (dateTime != null) {
            appendTextEntity("DATE/TIME: " + dateTime);
            //appendTextEntity("\n");
        }

        if (amount != null) {
            appendTextEntityBold("AMOUNT: " + amount);
            //appendTextEntity("\n");
        }

        if (remark != null) {
            appendTextEntity("REMARK: " + remark);
        }

        if (rrn != null) {
            appendTextEntity("TRANS REF: " + rrn);
        }

        if (description != null) {
            appendTextEntity("Description:");
            appendTextEntity(description);
        }

        if (cardScheme != null)
            appendTextEntity(cardScheme);

        if (cardNumber != null) {
            appendTextEntity(cardNumber);
            //appendTextEntity("\n");
        }

        if (cardHolderName != null) {
            appendTextEntity(cardHolderName);
            appendTextEntity("EXPIRY DATE: **/**");
        }

        if (authorizationCode != null) {
            appendTextEntity("AUTHORIZATION CODE: " + authorizationCode);
        }

        if (transactionStatus != null) {
            appendTextEntityFontSixteenCenter(transactionStatus);
        }

        if (responseCode != null)
            appendTextEntity("RESPONSE CODE: " + responseCode);

        if (aid != null)
            appendTextEntity("AID: " + aid);

        if (rrn != null)
            appendTextEntity("RRN: " + rrn);

        if (appName != null && appVersion != null) {
            appendTextEntity(appName + " " + appVersion);
        }

        if (receiptCopy != null) {
            appendTextEntity("Powered by NetPlus");
            appendTextEntityCenter(receiptCopy);
        }
        if (isReprint) {
            appendTextEntityCenter("REPRINT");
        }

        if (footNote != null){
            appendTextEntity("\n---------------------------------------");
            appendTextEntityCenter(footNote);
            appendTextEntity("---------------------------------------");
        }
        printLine();
        return getThis();
    }


    public T buildOld() {
        if (title != null)
            appendTextEntity(title);

        if (terminalId != null) {
            appendTextEntity("TERMINAL ID: " + terminalId);
        }

        if (mid != null) {
            appendTextEntity("MID: " + mid);
        }

        if (transactionType != null) {
            appendTextEntityFontSixteenCenter(transactionType);
        }

        if (stan != null)
            appendTextEntityBold("STAN: " + stan);

        if (dateTime != null) {
            appendTextEntity("DATE/TIME: " + dateTime);
            //appendTextEntity("\n");
        }

        if (amount != null) {
            appendTextEntityBold("AMOUNT: " + amount);
            //appendTextEntity("\n");
        }

        if (remark != null) {
            appendTextEntity("REMARK: " + remark);
        }

        if (rrn != null) {
            appendTextEntity("TRANS REF: " + rrn);
        }

        if (cardScheme != null) {
            appendTextEntity(cardScheme);
        }

        if (cardNumber != null) {
            appendTextEntity(cardNumber);
            //appendTextEntity("\n");
        }

        if (cardHolderName != null) {
            appendTextEntity(cardHolderName);
            appendTextEntity("EXPIRY DATE: **/**");
        }

        if (authorizationCode != null) {
            appendTextEntity("AUTHORIZATION CODE: " + authorizationCode);
        }

        if (transactionStatus != null) {
            appendTextEntityFontSixteenCenter(transactionStatus);
        }

        if (responseCode != null) {
            appendTextEntity("RESPONSE CODE: " + responseCode);
        }

        if (aid != null)
            appendTextEntity("AID: " + aid);

        if (rrn != null)
            appendTextEntity("RRN: " + rrn);

        if (appName != null && appVersion != null) {
            appendTextEntity(appName + " " + appVersion);
        }

        if (receiptCopy != null) {
            appendTextEntity("Powered by NetPlus");
            appendTextEntityCenter(receiptCopy);
        }
        printLine();
        return getThis();
    }

    public abstract void appendTextEntity(String str);

    public abstract void appendTextEntityLargeFontCenter(String str);

    public abstract void appendTextEntityBold(String str);

    public abstract void appendTextEntityFontSixteen(String str);

    public abstract void appendTextEntityFontSixteenCenter(String str);

    public abstract void appendTextEntityCenter(String str);

    public abstract void appendImageCenter(Bitmap bitmap);

    public abstract K print();
}
