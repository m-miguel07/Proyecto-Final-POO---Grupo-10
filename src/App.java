public class App {

    public static class Personaje{

        int vida;

        public Personaje(int vida){
            this.vida = vida;
        }

        public boolean estaVivo(){
            if(this.vida > 0){
                return true;
            }
            return false;
        }

        public void revivir(){
            this.vida = 100;
        }

        public void recibirDaño(int daño){
            this.vida = this.vida - daño;
        }
    }
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        Personaje personaje = new Personaje(100);
        personaje.recibirDaño(100);
        System.out.println(personaje.estaVivo());
        personaje.revivir();
        System.out.println(personaje.estaVivo());

    }
}
