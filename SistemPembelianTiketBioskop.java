import java.util.ArrayList;
import java.util.List;

// Superclass Tiket
abstract class Tiket {
    protected double harga;
    protected String nomorKursi;

    public Tiket(double harga, String nomorKursi) {
        this.harga = harga;
        this.nomorKursi = nomorKursi;
    }

    public double getHarga() {
        return harga;
    }

    public String getNomorKursi() {
        return nomorKursi;
    }
}

// Subclass VipTicket
class VipTicket extends Tiket {
    private String layananVip;

    public VipTicket(double harga, String nomorKursi, String layananVip) {
        super(harga, nomorKursi);
        this.layananVip = layananVip;
    }

    public String getLayananVip() {
        return layananVip;
    }
}

// Subclass RegularTicket
class RegularTicket extends Tiket {
    public RegularTicket(double harga, String nomorKursi) {
        super(harga, nomorKursi);
    }
}

// Class Film
class Film {
    private String judul;
    private int durasi; // durasi dalam menit
    private String rating;
    private List<Penonton> penontonList; // Association with Penonton

    public Film(String judul, int durasi, String rating) {
        this.judul = judul;
        this.durasi = durasi;
        this.rating = rating;
        this.penontonList = new ArrayList<>();
    }

    public void tambahPenonton(Penonton penonton) {
        penontonList.add(penonton);
    }

    public String getJudul() {
        return judul;
    }

    public int getDurasi() {
        return durasi;
    }

    public String getRating() {
        return rating;
    }

    public List<Penonton> getPenontonList() {
        return penontonList;
    }
}

// Class Bioskop
class Bioskop {
    private String nama;
    private String lokasi;
    private List<Film> filmList; // Aggregation with Film

    public Bioskop(String nama, String lokasi) {
        this.nama = nama;
        this.lokasi = lokasi;
        this.filmList = new ArrayList<>();
    }

    public void tambahFilm(Film film) {
        filmList.add(film);
    }

    public String getNama() {
        return nama;
    }

    public String getLokasi() {
        return lokasi;
    }

    public List<Film> getFilmList() {
        return filmList;
    }
}

// Class Penonton
class Penonton {
    private String idPenonton;
    private String nama;
    private Booking booking; // Composition with Booking

    public Penonton(String idPenonton, String nama) {
        this.idPenonton = idPenonton;
        this.nama = nama;
    }

    public void buatBooking(String idBooking, String jadwal, Tiket tiket, Film film) {
        this.booking = new Booking(idBooking, jadwal, tiket, this, film);
        film.tambahPenonton(this); // Menambahkan penonton ke film
    }

    public Booking getBooking() {
        return booking;
    }

    public String getIdPenonton() {
        return idPenonton;
    }

    public String getNama() {
        return nama;
    }
}

// Class Booking
class Booking {
    private String idBooking;
    private String jadwal;
    private Tiket tiket;
    private Penonton penonton;
    private Film film;

    public Booking(String idBooking, String jadwal, Tiket tiket, Penonton penonton, Film film) {
        this.idBooking = idBooking;
        this.jadwal = jadwal;
        this.tiket = tiket;
        this.penonton = penonton;
        this.film = film;
    }

    public String getIdBooking() {
        return idBooking;
    }

    public String getJadwal() {
        return jadwal;
    }

    public Tiket getTiket() {
        return tiket;
    }

    public Penonton getPenonton() {
        return penonton;
    }

    public Film getFilm() {
        return film;
    }
}

// Main class for testing
public class SistemPembelianTiketBioskop {
    public static void main(String[] args) {
        // Membuat objek bioskop dan film
        Bioskop bioskop = new Bioskop("Cinema XXI", "Mall Central Park");
        Film film1 = new Film("Avatar", 162, "PG-13");
        Film film2 = new Film("Interstellar", 169, "PG-13");

        bioskop.tambahFilm(film1);
        bioskop.tambahFilm(film2);

        // Membuat penonton dan tiket
        Penonton penonton1 = new Penonton("P001", "Alice");
        Penonton penonton2 = new Penonton("P002", "Bob");

        Tiket regularTicket = new RegularTicket(50000, "A10");
        VipTicket vipTicket = new VipTicket(100000, "VIP01", "Snack Gratis");

        // Membuat booking untuk penonton
        penonton1.buatBooking("B001", "18:00", regularTicket, film1);
        penonton2.buatBooking("B002", "20:00", vipTicket, film2);

        // Menampilkan hasil
        System.out.println("Bioskop: " + bioskop.getNama() + ", Lokasi: " + bioskop.getLokasi());
        System.out.println("Film yang tersedia:");
        for (Film film : bioskop.getFilmList()) {
            System.out.println("- " + film.getJudul() + " (" + film.getDurasi() + " menit, Rating: " + film.getRating() + ")");
        }

        System.out.println("\nBooking Penonton:");
        System.out.println(penonton1.getNama() + " memesan kursi " + penonton1.getBooking().getTiket().getNomorKursi() + 
                           " untuk film " + penonton1.getBooking().getFilm().getJudul() + " pada jadwal " + penonton1.getBooking().getJadwal());
        System.out.println(penonton2.getNama() + " memesan kursi " + penonton2.getBooking().getTiket().getNomorKursi() + 
                           " untuk film " + penonton2.getBooking().getFilm().getJudul() + " pada jadwal " + penonton2.getBooking().getJadwal() + 
                           " dengan layanan VIP: " + ((VipTicket) penonton2.getBooking().getTiket()).getLayananVip());
    }
}