import java.util.Scanner;

/**
 * Nur Sultan ASLAN (NÖ)
 * 02180201056
 */

public class Main {
    public static void main(String[] args) {

        String input; // kullanıcıdan alıncak metin değişkeni
        Scanner sc = new Scanner(System.in); // girdi alınabilmesi için gerekli sınıf

        // huffman algoritması sınıfı
        HuffmanEncoder encoder = new HuffmanEncoder();

        System.out.print("Kodlanacak ifadeyi giriniz: ");

        // abcd gibi 4 farklı karakter içeren metinler huffman algoritması ile binary olarak sıkıştırılabilir

        // örnek girdiler:
        //input = "aaabbcddddd";
        //input = "abbcccdddd";
        //input = "abbccc";
        //input = "abb";
        //input = "cccddbaaaa";
        //input = "daaabbcccccc";

        // kullanıcıdan alınan string değer input değişkenine atanır
        input = sc.next();

        System.out.println();
        System.out.println("Metin: " + input);

        // kullanıcıdan alınan metin encoder sınıfındaki generate() fonksiyonuna gönderilerek
        // binary karşılığı hesaplanır ve ekrana yazdırılır
        encoder.generate(input);
    }
}