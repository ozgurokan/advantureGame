import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Game {
    private Scanner input = new Scanner(System.in);

    public void start() {
        System.out.println("Macera Oyununa Hoşgeldiniz ! ");
        System.out.print("İsminizi giriniz: ");
        // String playerName = input.nextLine();

        Player player = new Player("okan");
        //System.out.println(player.getName() + " Hoşgeldin, Hazır ol Macera Başlıyor...");
        System.out.println("Birazdan yaşamaya başlayacaklarının hepsi gerçek...");

        System.out.println("Bu maceraya hangi karakterle atılacaksın?");
        player.selectChar();

        Location location = null;
        while (true) {

            if(player.getInventory().isWater() && player.getInventory().isFood() && player.getInventory().isFireWood()){
                System.out.println("Cesurca ve akıllıca savaşıp tüm bölge ödüllerini topladın. Tebrikler macerayı zaferle sonlandırdın");
                break;
            }
            player.printInfo();
            System.out.println("\n------------------------ Bölgeler ------------------------\n");
            System.out.println("1 - Güvenli Ev --> Düşman yok, Canınız yenilenir!");
            System.out.println("2 - Dükkan --> Eşya satın alabilirsiniz!");
            System.out.println("3 - Mağara --> Mağaraya Gir! - Tehlikeli Bölge! - Ödül --> Yemek");
            System.out.println("4 - Orman --> Ormana Gir! - Tehlikeli Bölge! - Ödül --> Odun");
            System.out.println("5 - Nehir --> Nehire Gir! - Tehlikeli Bölge! - Ödül --> Su");
            System.out.println("6 - Maden --> Madene Gir! - Tehlikeli Bölge! - Rastgele Eşya ve Para");
            System.out.println("0 - Çıkış yap --> Oyunu Sonlandır");
            System.out.print("\nGitmek istedğiniz bölgeyi seçiniz: ");
            int selectLoc = input.nextInt();
            switch (selectLoc) {
                case 0:
                    location = null;
                    break;
                case 1:
                    location = new SafeHouse(player);
                    break;
                case 2:
                    location = new ToolStore(player);
                    break;
                case 3:
                    if (!player.getInventory().isFood()) {
                        location = new Cave(player);
                    } else {
                        System.out.println("Bu bölgeyi temizledin ve ödülü aldın. Artık Buraya Giremezsin!");
                        location = new SafeHouse(player);
                    }
                    break;
                case 4:
                    if (!player.getInventory().isFireWood()) {
                        location = new Forest(player);
                    } else {
                        System.out.println("Bu bölgeyi temizledin ve ödülü aldın. Artık Buraya Giremezsin!");
                        location = new SafeHouse(player);

                    }
                    break;
                case 5:
                    if (!player.getInventory().isWater()) {
                        location = new River(player);
                    } else {

                        System.out.println("Bu bölgeyi temizledin ve ödülü aldın. Artık Buraya Giremezsin!");
                        location = new SafeHouse(player);
                    }
                    break;
                case 6:
                    location = new Mine(player);
                    break;
                default:
                    location = new SafeHouse(player);
                    System.out.println("Güvenli eve gidiyorsunuz");
            }

            if (location == null) {
                System.out.println("Oyun Bitirildi...Maceradan çabuk vazgeçtin! ");
                break;
            }
            if (!location.onLocation()) {
                System.out.println("Oyun Bitti !");
                break;
            }

        }


    }
}
