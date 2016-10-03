package URLExceptions;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Jatinder Dhawan on 06/3/2016.
 */
public class MyException {
    public static void main(String[] args) throws IOException {
        int number = 0;
        try {
            PrintWriter printwriter = new PrintWriter("outputOfCrawler.md");
            printwriter.println("OUTPUT OF ALL PRODUCTS STARTING FROM 'J'");
            printwriter.println("======================");

            Document document = Jsoup.connect("http://www.cvedetails.com/product-list/firstchar-J/vendor_id-0/products.html").get();
            Elements allPages =  document.select("div[id=pagingb]");
            Elements pageLinks = allPages.select("a[href]");
            //System.out.println(pageLinks);
            for(Element pageLink : pageLinks) {
                 //  System.out.print("1. ");
                // System.out.println(pageLink);
                Document singlePage = Jsoup.connect("http://cvedetails.com"+pageLink.attr("href")).get();
                Elements productslink = singlePage.select("table[class=listtable]") ;
                Elements product = productslink.select("a[href]") ;
                int j = 0;
                for(Element pr : product) {
                        j++;
                    if((j-5)%7 == 0 ) {
                        printwriter.println("### " + pr.text());
                        printwriter.println("| Attribute | Value |");
                        printwriter.println("| :------- | :------- |");
                        Document singleDocument = Jsoup.connect("http:"+pr.attr("href")).get();
                        Elements eachPageTable = singleDocument.select("table[class=stats]");
                        Elements rows = eachPageTable.select("tr");
                        Element headings = rows.get(0);
                        Element values = rows.get(rows.size() -1);
                        Elements rowHeadings = headings.select("th");
                        Elements data = values.select("td");

                        for(int i = 1 ;i<data.size() ;i++) {
                            printwriter.println("| " + rowHeadings.get(i).text() + " | " + data.get(i-1).text() + " | ");
                            System.out.println("Printing...."+number++);
                        }
                        printwriter.println("\n");

                     }

                }

            }
            printwriter.close();
          //  pw.close();
        }
        catch(Exception e) {
            System.out.println("An exception is thrown titled : "+ e );
        }

    }
}
