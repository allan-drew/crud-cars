package crud.dominio;

import java.util.Objects;

public class Carros {

    private Integer id;
    private String marca;
    private String modelo;
    private Integer quilometragem;


    public static final class CarrosBuilder {

        private Integer id;
        private String marca;
        private String modelo;
        private Integer quilometragem;

        private CarrosBuilder() {
        }

        public static CarrosBuilder builder() {
            return new CarrosBuilder();
        }

        public CarrosBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public CarrosBuilder marca(String marca) {
            this.marca = marca;
            return this;
        }

        public CarrosBuilder modelo(String modelo) {
            this.modelo = modelo;
            return this;
        }

        public CarrosBuilder quilometragem(Integer quilometragem) {
            this.quilometragem = quilometragem;
            return this;
        }

        public Carros build() {
            Carros carros = new Carros();
            carros.modelo = this.modelo;
            carros.quilometragem = this.quilometragem;
            carros.id = this.id;
            carros.marca = this.marca;
            return carros;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carros carros = (Carros) o;
        return Objects.equals(id, carros.id) && Objects.equals(marca, carros.marca) && Objects.equals(modelo, carros.modelo) && Objects.equals(quilometragem, carros.quilometragem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, marca, modelo, quilometragem);
    }


    public Integer getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public Integer getQuilometragem() {
        return quilometragem;
    }


    @Override
    public String toString() {
        return "Carros { " + '\n' +
                "   id = " + id + '\n' +
                "   marca = " + marca + '\n' +
                "   modelo = " + modelo + '\n' +
                "   quilometragem = " + quilometragem + '\n' +
                '}';
    }


}
