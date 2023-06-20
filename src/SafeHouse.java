public class SafeHouse extends NormalLocation {

    public SafeHouse(Player player) {
        super(player, "Güvenli Ev");
    }

    @Override
    public boolean onLocation() {

        System.out.println("Güvenli Evdesiniz");
        System.out.println("Canınız Yenileniyor");
        System.out.println("------------------------------------------");

        this.getPlayer().setHealth(this.getPlayer().getDefaultHealth());

        return true;
    }
}
