public class App {

    public static class Personaje{

        int vida;

        public Personaje(int vida){
            this.vida = vida;
        }

        public boolean estaVivo(){
            return this.vida > 0;
        }

        public void revivir(){
            this.vida = 100;
        }

        public void recibirDanio(int danio){
            this.vida = this.vida - danio;
        }
    }
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        Personaje personaje = new Personaje(100);
        personaje.recibirDanio(100);
        System.out.println(personaje.estaVivo());
        personaje.revivir();
        System.out.println(personaje.estaVivo());

    }
}
