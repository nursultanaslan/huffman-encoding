import java.util.*;
import java.util.stream.Collectors;

/**
 * Nur Sultan ASLAN (NÖ)
 * 02180201056
 */

public class HuffmanEncoder {

    // frekans değerleri bu dizide saklanır
    private HashMap<Character, Integer> freq = new HashMap<>();

    // binary kodlar ekrana yazdırılması için bu dizide tutulur
    private HashMap<Character, String> result = new HashMap<>();

    // yapıcı fonksiyon
    HuffmanEncoder() {
    }

    /**
     *  string olarak gönderilen metin karakterlere ayrılır ve her bir karakterin
     *  frekansı freq dizisine atanır
     *
     * @param input: kullanıcı tarafından gönderilen metin
     * @param index: metindeki kontrol edilecek karakter indisi
     * @return: index + 1 return edilerek bir sonraki karaktere geçilir
     */
    private int splitChars(String input, int index) {
        if (index >= input.length()) { // tüm karakterler recursive olarak gezilir
            return 0; // son karaktere ulaşılırsa 0 return edilerek fonksiyondan çıkılır
        }

        Character charAtIndex = input.charAt(index); // geçerli indisteki karakter

        // mevcut döngüdeki karakterin freq dizisindeki frekans değerine 1 eklenir
        Integer count = freq.get(charAtIndex);
        Integer frequency = count != null ? count + 1 : 1;
        freq.put(charAtIndex, frequency);

        // sonraki karaktere geçilir
        return splitChars(input, index + 1);
    }

    /**
     * metin huffman algoritması ile binary kodlara dönüştürülür
     * @param input: kullanıcı tarafından gönderilen metin
     * @return
     */
    public void generate(String input) {

        // iki frekans değerinin toplamı tutulur
        Integer toplam = 0;

        // metin karakterlere ayrılır ve frekanslarına göre kaydedilir
        splitChars(input, 0);

        // frekans listesi küçükten büyüğe sıralanır
        freq = freq.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (a,b)->b, LinkedHashMap::new));

        // index değişkeni sırayla koda çevrilecek karakterlerin indisini tutar
        int index = 0;

        // keys dizisi frekans değerlerinin karakter karşılığını tutar
        Object[] keys = freq.keySet().toArray();

        // arr dizisi karakterlerin binary kod karşılıklarını saklar
        String[] arr = new String[freq.size()];

        // iki karakteri karşılaştırmak için geçici değişkenler
        Character char1, char2;

        // mevcut karakterin değeri için value geçici değişkeni kullanılır
        Integer value;

        // tüm karakterler kodlanana kadar karakterleri gezer
        while(index < freq.size()) {

            // mevcut indisteki birinci karakter atanır
            char1 = (Character) keys[index];

            // freq dizisindeki "index" indisindeki frekans değeri geçici değişkene atanır
            value = freq.get(keys[index]);

            // index sıfır ise en az frekans değerine sahip iki karakter birbiriyle toplanır.
            if(index == 0) {

                // daha düşük frekanslı karaktere 0 binary kodu eklenir
                arr[index] = "0" + arr[index];

                // daha yüksek frekanslı karaktere 1 binary kodu eklenir
                arr[index+1] = "1" + arr[index+1];

                // ikinci karakter geçici değişkene atanır
                char2 = (Character) keys[index+1];

                // listedeki en düşük frekanslı iki karakterin frekansları toplanır ve
                // toplam değişkenine atanır. böylece sonraki karakter kontrollerinde karakter
                // frekansı ile önceki iki karakterin frekansları toplamı karşılaştırılır
                toplam = freq.get(char1) + freq.get(char2);
                index+=2; // iki adet karakter kodlandığı için index e 2 eklenir
            }

            // kodlanacak karakter ilk iki karakter değilse
            else {

                // mevcut karakterin değeri, önceki eklenen karakterlerin toplamından büyük ise
                // 1 binary kodu karakterin kod karşılığına eklenir.

                // bu karakterin altında bulunan diğer karakterlerin kod karşılıklarına ise
                // bu karakterin solunda kaldıkları için 0 biti eklenir
                if(value > toplam) {

                    toplam = toplam + value; // mevcut frekans ile önceki frekanslar toplanır
                    arr[index] = "1" + arr[index]; // mevcut frekans büyük olduğundan binary kodlarına 1 biti eklenir

                    // bu karakterine altında kalan karakterlerin binary karşılıklarına 0 biti eklenir
                    for(int i = 0; i < index; i++) {
                        arr[i] = "0" + arr[i];
                    }
                }

                // mevcut karakterin değeri, önceki eklenen karakterlerin toplamından küçük ise
                // 0 binary kodu karakterin kod karşılığına eklenir.
                else {

                    // bu karakterin altında bulunan diğer karakterlerin kod karşılıklarına ise
                    // bu karakterin sağında kaldıkları için 1 biti eklenir
                    toplam = toplam + value; // mevcut frekans ile önceki frekanslar toplanır
                    arr[index] = "0" + arr[index]; // mevcut frekans küçük olduğundan binary kodlarına 0 biti eklenir

                    // bu karakterine altında kalan karakterlerin binary karşılıklarına 1 biti eklenir
                    for(int i = 0; i < index; i++) {
                        arr[i] = "1" + arr[i];
                    }
                }

                // karakterin binary karşılığa ekleme yapıldığı için index değeri 1 arttırılır
                index+=1;
            }
        }

        // karakterin binary karşılıkları oluşturulduğundan ekrana yazdılır
        index = 0;

        // frekans dizisindeki anahtar değerleri gezilir
        for(Character key : freq.keySet()) {

            // result dizisine, arr dizisindeki binary kod karşılı eklenir
            // Integer wrapper nesnesindeki null değeri silinir
            result.put(key, arr[index++].replace("null", ""));
        }

        // frekans değerleri yazdırılır.
        System.out.println("Frekans Değerleri: " + freq);

        // karakterlerin sıkıştırılmış binary karşılıkları yazdırılır
        System.out.println("Binary Kod Karşılıkları: " + result);
    }
}
