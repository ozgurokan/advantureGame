import java.util.Random;
import java.util.Scanner;

public class Mine extends BattleLocation {
    private Scanner input = new Scanner(System.in);

    public Mine(Player player) {
        super(player, "Maden", new Snake(), "none", 5);

    }

    @Override
    public boolean onLocation() {
        int obsNumber = this.randomObstacleNumber();
        System.out.println("Şu an buradasınız: " + this.getName());
        System.out.println("Dikkatli Ol! Burada " + obsNumber + " tane " + this.getObstacle().getName() + " Yaşıyor");
        System.out.print("(S)avaş veya (K)aç: ");
        String selectBattleCase = input.nextLine();
        selectBattleCase = selectBattleCase.toUpperCase();

        if (selectBattleCase.equals("S")) {
            if (combat(obsNumber)) {
                System.out.println(this.getName() + " Bölgesindeki Tüm Düşmanları Temizledin!");
                return true;
            }
            if (this.getPlayer().getHealth() <= 0) {
                System.out.println("Öldünüz!");
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean combat(int obsNumber) {

        for (int i = 1; i <= obsNumber; i++) {
            int firstHitChance = randomNumberGen();
            this.getObstacle().setHealth(this.getObstacle().getDefaultHealth());
            playerStats();
            obstacleStats(i);
            if (firstHitChance < 50) {
                while (this.getPlayer().getHealth() > 0 && this.getObstacle().getHealth() > 0) {
                    System.out.print("(V)ur veya (K)aç: ");
                    String selectCombat = input.nextLine().toUpperCase();
                    if (selectCombat.equals("V")) {
                        System.out.println("Vuruyorsunuz! ");
                        this.getObstacle().setHealth(this.getObstacle().getHealth() - this.getPlayer().getTotalDamage());
                        afterHit();
                        if (this.getObstacle().getHealth() > 0) {
                            System.out.println();
                            System.out.println("Canavar Saldırıyor!");
                            int obstacleDamage = this.getObstacle().getDamage() - this.getPlayer().getInventory().getArmor().getBlock();
                            if (obstacleDamage < 0) {
                                obstacleDamage = 0;
                            }
                            this.getPlayer().setHealth(this.getPlayer().getHealth() - obstacleDamage);
                            afterHit();
                        }
                    } else {
                        return false;
                    }
                }
                if (this.getObstacle().getHealth() < this.getPlayer().getHealth()) {
                    System.out.println("Canavar Öldürüldü!");
                    randomAward();
                    this.getPlayer().setMoney(this.getPlayer().getMoney() + this.getObstacle().getMoneyAward());
                    System.out.println("-----------------------------------------------------");
                } else {
                    return false;

                }
            } else {
                while (this.getPlayer().getHealth() > 0 && this.getObstacle().getHealth() > 0) {
                    System.out.println("Canavar Saldırıyor!");
                    int obstacleDamage = this.getObstacle().getDamage() - this.getPlayer().getInventory().getArmor().getBlock();
                    if (obstacleDamage < 0) {
                        obstacleDamage = 0;
                    }
                    this.getPlayer().setHealth(this.getPlayer().getHealth() - obstacleDamage);
                    afterHit();
                    if (this.getPlayer().getHealth() > 0) {
                        System.out.print("(V)ur veya (K)aç: ");
                        String selectCombat = input.nextLine().toUpperCase();
                        if (selectCombat.equals("V")) {
                            System.out.println("Vuruyorsunuz! ");
                            this.getObstacle().setHealth(this.getObstacle().getHealth() - this.getPlayer().getTotalDamage());
                            afterHit();
                        } else {
                            return false;
                        }
                    }
                }
                if (this.getObstacle().getHealth() < this.getPlayer().getHealth()) {
                    System.out.println("Canavar Öldürüldü!");
                    randomAward();
                    this.getPlayer().setMoney(this.getPlayer().getMoney() + this.getObstacle().getMoneyAward());
                    System.out.println("-----------------------------------------------------");
                } else {
                    return false;

                }
            }
        }
        return true;
    }

    public void randomAward() {
        Random r = new Random();
        int firstChance = r.nextInt(0, 100);
        int secondChance = r.nextInt(0, 100);

        //silah kazanma ihtimali
        if (firstChance <= 15) {
            int earnedWeaponID=0;

            if (secondChance <= 20) {
                earnedWeaponID = 3;
            }
            if (secondChance > 20 && secondChance <= 50) {
                earnedWeaponID = 2;
            }
            if (secondChance > 50 && secondChance <= 100) {
                earnedWeaponID = 1;
            }
            System.out.println(Weapon.getWeaponObjByID(earnedWeaponID).getName() + " Kazandınız");
            // Kullanmak istiyor mu?
            if(isAcceptAward()){
                this.getPlayer().getInventory().setWeapon(Weapon.getWeaponObjByID(earnedWeaponID));
                System.out.println(Weapon.getWeaponObjByID(earnedWeaponID).getName() + " Envanterine Eklendi!");
            }
        }
        //zırh kazanma ihtimali
        if (firstChance >15 && firstChance <=30) {
            int earnedArmorID=0;
            if (secondChance <= 20) {
                earnedArmorID = 3;
            }
            if (secondChance > 20 && secondChance <= 50) {
                earnedArmorID=2;
            }
            if (secondChance > 50 && secondChance <= 100) {
                earnedArmorID=1;
            }
            System.out.println(Armor.getArmorObjByID(earnedArmorID).getName() + " Zırh Kazandınız");
            // Kullanmak istiyor mu?
            if(isAcceptAward()){
                this.getPlayer().getInventory().setArmor(Armor.getArmorObjByID(earnedArmorID));
                System.out.println(Armor.getArmorObjByID(earnedArmorID).getName() + " Zırh Envarterine Eklendi!");
            }
        }
        // Para Kazanma ihtimali
        if (firstChance >30 && firstChance<= 55) {
            if (secondChance <= 20) {
                this.getPlayer().setMoney(this.getPlayer().getMoney() + 10);
                System.out.println("10 Para Kazandınız");
            }
            if (secondChance > 20 && secondChance <= 50) {
                this.getPlayer().setMoney(this.getPlayer().getMoney() + 5);
                System.out.println("5 Para Kazandınız");
            }
            if (secondChance > 50 && secondChance <= 100) {
                this.getPlayer().setMoney(this.getPlayer().getMoney() + 1);
                System.out.println("1 Para Kazandınız");
            }
        }

    }

    public boolean isAcceptAward() {
        System.out.println("Ödülü Kullanmak istiyor musunuz?");
        System.out.print("(E)vet - (H)ayır: ");
        String acceptKey = input.nextLine().toUpperCase();


        if (acceptKey.equals("E")) {
            return true;
        } else {
            return false;
        }
    }

}
