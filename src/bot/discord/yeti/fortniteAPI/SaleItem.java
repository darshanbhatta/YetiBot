package bot.discord.yeti.fortniteAPI;



public class SaleItem{

    private final String title;

    private final String price;

    private final String imgSrc;


    public String getType() {
        return type;
    }


    private final String type;


    public SaleItem(final String title,
                   final String price,
                    final String imgSrc,
                  final String type) {
        this.title = title;

     this.price = price;
        this.imgSrc = imgSrc;
        this.type = type;
    }

    @Override
    public String toString() {
        return "SaleItem{" +
                "title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                '}';
    }


    public String getTitle() {
        return title;
    }


    public String getPrice() {
        return price;
    }


    public String getImgSrc() {
        return imgSrc;
    }


}
