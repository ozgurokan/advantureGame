import java.util.Scanner;

public class Player {
    private int damage;
    private int health;
    private int defaultHealth;
    private int money;
    private String name;
    private String charName;
    private Scanner input = new Scanner(System.in);
    private  Inventory inventory;


    // constructor
    public Player(String name) {
        this.name = name;
        this.inventory = new Inventory();
    }

    public void selectChar() {
        GameCharacter[] charlist = {new Samurai(), new Archer(), new Knight()};
        System.out.println("");
        System.out.println("------------------------Karakterler--------------------------------");
        for (GameCharacter gameCharacter : charlist) {

            System.out.println("ID: " + gameCharacter.getId() + " -- "
                    + gameCharacter.getName() + "-->\tHasar: "
                    + gameCharacter.getDamage() + "\tSağlık: "
                    + gameCharacter.getHealth() + "\t Para: "
                    + gameCharacter.getMoney());
        }
        System.out.println("-------------------------------------------------------------------");
        System.out.print("\nLütfen bir karakter seçiniz :");
        int selectedChar = input.nextInt();
        switch (selectedChar) {
            case 1:
                initPlayer(new Samurai());
                break;
            case 2:
                initPlayer(new Archer());
                break;
            case 3:
                initPlayer(new Knight());
                break;
            default:
                System.out.println("Geçersiz karakter seçtiniz...Varsayılan olarak Knight aldınız");

        }
        System.out.println("Seçilen karakter: " + this.getCharName());

    }


    public void initPlayer(GameCharacter gameCharacter) {
        this.setDamage(gameCharacter.getDamage());
        this.setHealth(gameCharacter.getHealth());
        this.setDefaultHealth(gameCharacter.getHealth());
        this.setMoney(gameCharacter.getMoney());
        this.setCharName(gameCharacter.getName());

    }
    public void printInfo(){
        System.out.println(
                "Silah: " + this.getInventory().getWeapon().getName()
                + ", Zırh: " + this.getInventory().getArmor().getName()
                + ", Hasar: " + this.getTotalDamage()
                + ", Sağlık: " + this.getHealth()
                + ", Para: " + this.getMoney()
                + ", Engelleme: " + this.getInventory().getArmor().getBlock()
        );
    }

    // Getter-Setter
    public int getTotalDamage(){
        return damage + this.getInventory().getWeapon().getDamage();
    }
    public int getDamage() {
       return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName = charName;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public int getDefaultHealth(){
        return defaultHealth;
    }
    public void setDefaultHealth(int defaultHealth){
        this.defaultHealth = defaultHealth;
    }
}
