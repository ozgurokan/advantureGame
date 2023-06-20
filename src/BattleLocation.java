import java.util.Random;
import java.util.Scanner;

public abstract class BattleLocation extends Location {
    private Obstacle obstacle;
    private String award;
    private int maxObstacle;

    public BattleLocation(Player player, String name, Obstacle obstacle, String award, int maxObstacle) {
        super(player, name);
        this.obstacle = obstacle;
        this.award = award;
        this.maxObstacle = maxObstacle;
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
                this.addLocationAward(this.getAward());
                System.out.println(this.getAward() + " Ödülü Envanterine Eklendi");
                return true;
            }
            if (this.getPlayer().getHealth() <= 0) {
                System.out.println("Öldünüz!");
                return false;
            }
        }
        return true;
    }

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
                    System.out.println(this.getObstacle().getMoneyAward() + " Para Kazandınız");
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
                        }else {
                            return false;
                        }
                    }
                }if (this.getObstacle().getHealth() < this.getPlayer().getHealth()) {
                    System.out.println("Canavar Öldürüldü!");
                    System.out.println(this.getObstacle().getMoneyAward() + " Para Kazandınız");
                    this.getPlayer().setMoney(this.getPlayer().getMoney() + this.getObstacle().getMoneyAward());
                    System.out.println("-----------------------------------------------------");
                } else {
                    return false;

                }
            }
        }
        return true;
    }

    public int randomNumberGen() {
        Random r = new Random();
        return r.nextInt(0, 100);
    }

    public void addLocationAward(String award) {
        if (this.getAward() == "food") {
            this.getPlayer().getInventory().setFood(true);
        }
        if (this.getAward() == "firewood") {
            this.getPlayer().getInventory().setFireWood(true);
        }
        if (this.getAward() == "water") {
            this.getPlayer().getInventory().setWater(true);
        }
    }

    public void afterHit() {
        System.out.println("---------------******---------------------");
        System.out.println("Canınız: " + this.getPlayer().getHealth());
        System.out.println(this.getObstacle().getName() + " Sağlığı: " + this.getObstacle().getHealth());
        System.out.println("---------------******---------------------");
    }

    public void playerStats() {
        System.out.println("-------------------Oyuncu Değerleri------------------");
        System.out.println();
        System.out.println("Zırh: " + this.getPlayer().getInventory().getArmor().getName()
                + ", Bloklama: " + this.getPlayer().getInventory().getArmor().getBlock()
                + ", Sağlık: " + this.getPlayer().getHealth()
                + ", Silah: " + this.getPlayer().getInventory().getWeapon().getName()
                + ", Hasar: " + this.getPlayer().getTotalDamage()
                + ", Para: " + this.getPlayer().getMoney());
        System.out.println();

    }

    public void obstacleStats(int i) {
        System.out.println("------------------" + i + ". " + this.getObstacle().getName() + " Değerleri-----------------");
        System.out.println();
        System.out.println(" Sağlık: " + this.getObstacle().getHealth()
                + ", Hasar: " + this.getObstacle().getDamage()
                + ", Ödül: " + this.getObstacle().getMoneyAward());
        System.out.println();
        System.out.println("-----------------------------------------------------");

    }

    public int randomObstacleNumber() {
        Random r = new Random();
        return r.nextInt(this.getMaxObstacle()) + 1;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public int getMaxObstacle() {
        return maxObstacle;
    }

    public void setMaxObstacle(int maxObstacle) {
        this.maxObstacle = maxObstacle;
    }
}
