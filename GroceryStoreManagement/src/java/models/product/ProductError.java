package models.product;

public class ProductError {
    private String nameErr;
    private String costPriceErr;
    private String sellingPriceErr;
    private String lowerThresholdErr;

    public ProductError() {
    }

    public String getNameErr() {
        return nameErr;
    }

    public void setNameErr(String nameErr) {
        this.nameErr = nameErr;
    }


    public String getCostPriceErr() {
        return costPriceErr;
    }

    public void setCostPriceErr(String costPriceErr) {
        this.costPriceErr = costPriceErr;
    }

    public String getSellingPriceErr() {
        return sellingPriceErr;
    }

    public void setSellingPriceErr(String sellingPriceErr) {
        this.sellingPriceErr = sellingPriceErr;
    }

    public String getLowerThresholdErr() {
        return lowerThresholdErr;
    }

    public void setLowerThresholdErr(String lowerThresholdErr) {
        this.lowerThresholdErr = lowerThresholdErr;
    }

}
