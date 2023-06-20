import java.sql.SQLOutput;

public class ToolStore extends NormalLocation {

    public ToolStore(Player player) {
        super(player, "Mağaza");
    }

    @Override
    public boolean onLocation() {
        System.out.println("------------ Mağazaya Hoşgeldin ! ------------");

        boolean showMenuKey = true;
        while (showMenuKey) {
            System.out.println("1 --> Silahlar");
            System.out.println("2 --> Zırhlar");
            System.out.println("0 --> Çıkış Yap");
            System.out.print("Seçiminiz: ");
            int selectCase = input.nextInt();
            input.nextLine(); // input almama bug'ını çözüyor
            while (selectCase < 0 || selectCase > 2) {
                System.out.print("Geçersiz seçim yaptınız, tekrar girin: ");
                selectCase = input.nextInt();
            }
            switch (selectCase) {
                case 0:
                    System.out.println("Bir daha bekleriz");
                    showMenuKey = false;
                    break;
                case 1:
                    printWeapons();
                    buyNewWeapon();
                    break;
                case 2:
                    printArmors();
                    buyNewArmor();
                    break;
            }


        }
        return true;

    }

    public void printWeapons() {
        System.out.println("-----Silahlar-----");
        for (Weapon w : Weapon.weapons()) {
            System.out.println("ID: " + w.getId() + " " + w.getName() + "\tPara: " + w.getPrice() + ", Hasar : " + w.getDamage());

        }
        System.out.println("0 - Mağaza Menüsü");

    }

    public void buyNewWeapon() {
        System.out.print("Bir Silah Seçiniz: ");

        int selectWeaponID = input.nextInt();
        while (selectWeaponID < 0 || selectWeaponID > Weapon.weapons().length) {
            System.out.print("Geçersiz seçim yaptınız, tekrar girin : ");
            selectWeaponID = input.nextInt();
        }
        if (selectWeaponID != 0) {
            Weapon selectedWeapon = Weapon.getWeaponObjByID(selectWeaponID);

            if (selectedWeapon != null) {
                if (selectedWeapon.getPrice() > this.getPlayer().getMoney()) {
                    System.out.println("Paranız Yetersiz");
                } else {

                    int balance = this.getPlayer().getMoney() - selectedWeapon.getPrice();
                    this.getPlayer().setMoney(balance);

                    System.out.println("Kalan Para: " + this.getPlayer().getMoney());
                    this.getPlayer().getInventory().setWeapon(selectedWeapon);

                    System.out.println(selectedWeapon.getName() + "Satın alındı");
                }
            }
        }
    }

    public void printArmors() {
        System.out.println("-----Zırhlar-----");
        for (Armor a : Armor.armors()) {
            System.out.println("ID: " + a.getId() + " " + a.getName() + "\tPara: " + a.getPrice() + ", Engelleme : " + a.getBlock());

        }
        System.out.println("0 - Mağaza Menüsü");
    }

    public void buyNewArmor() {
        System.out.print("Bir Zırh Seçiniz: ");

        int selectArmorID = input.nextInt();
        while (selectArmorID < 0 || selectArmorID > Armor.armors().length) {
            System.out.print("Geçersiz seçim yaptınız, tekrar girin : ");
            selectArmorID = input.nextInt();
        }
        if (selectArmorID != 0) {
            Armor selectedArmor = Armor.getArmorObjByID(selectArmorID);

            if (selectedArmor != null) {
                if (selectedArmor.getPrice() > this.getPlayer().getMoney()) {
                    System.out.println("Paranız Yetersiz");
                } else {
                    System.out.println(selectedArmor.getName() + " Satın alındı");

                    int balance = this.getPlayer().getMoney() - selectedArmor.getPrice();
                    this.getPlayer().setMoney(balance);

                    System.out.println("Kalan Para: " + this.getPlayer().getMoney());
                    this.getPlayer().getInventory().setArmor(selectedArmor);
                }
            }
        }
    }
}
