SNACC 
(versi developer-friendly, pakai bahasa lugas, ada humor tipis — tapi tetap profesional)

Ringkasan singkat

SNACC adalah prototype aplikasi pemesanan makanan sederhana berbasis Android.
Tujuannya: demonstrasi UI/UX, state management sederhana, navigasi, RecyclerView dengan layout toggle (grid/list), flow login → dashboard → order → confirm → thanks, serta visual polish (animasi, modal, blur).
Aplikasi ini tidak terhubung ke backend nyata—menggunakan dummy data dan SharedPreferences untuk sesi.

Akun Dummy (untuk pengujian)

Silakan pakai salah satu akun ini untuk login:

Username: Dffanzm — Password: Bandung22

Username: AgungPermanen — Password: ewinghd

Username: Mungkung — Password: Persib

Catatan: akun-akun ini hanya ada di LoginActivity sebagai dummyUsers map.

Fitur Utama & Alur Aplikasi

Splash / Main screens: animasi blur/fade di MainActivity → navigasi ke SigninActivity.

Register & Login: RegisterActivity (validasi dasar) dan LoginActivity (cek dummy, simpan session ke SharedPreferences).

Session disimpan dengan kunci user_session:

isLoggedIn (Boolean)

username (String)

Implementasi ada mekanisme reset session (opsional) untuk development.

Dashboard (DashboardActivity):

Menampilkan daftar makanan (10 item dummy) di RecyclerView.

Adapter: FoodAdapter dengan dua callback: onAddToCart & onOrderNow.

Tombol toggle layout: ganti antara Grid (2 kolom) dan List (LinearLayoutManager).

Bottom navigation (menu: Beranda, Keranjang, Profil) — saat ini menampilkan Toast, tempatkan logic navigasi nyata di CartActivity / ProfileActivity jika ingin.

Order flow:

Dari DashboardActivity → tekan Order Sekarang pada item → OrderActivity (modal-like atau activity) menampilkan username dan rincian pesanan (dari Intent).

Dari OrderActivity bisa lanjut ke ConfirmOrderActivity.

ConfirmOrderActivity:

Form input: Nama Lengkap, Alamat, Patokan.

Tombol Kirim Pesanan menjalankan validasi sederhana, kemudian Intent ke ThanksActivity.

ThanksActivity:

Ditampilkan sebagai modal (dialog-style) dengan animasi slide-up + fade.

Background di-blur (Android 12+, RenderEffect) atau fallback semi-transparan untuk versi Android lebih lama.

Tombol Tutup menutup modal.

Struktur Project (penting — cepat lihat)
app/
 ├─ src/main/
 │   ├─ java/com/example/snacc/
 │   │   ├─ MainActivity.kt
 │   │   ├─ SigninActivity.kt
 │   │   ├─ LoginActivity.kt
 │   │   ├─ RegisterActivity.kt
 │   │   ├─ DashboardActivity.kt
 │   │   ├─ OrderActivity.kt
 │   │   ├─ ConfirmOrderActivity.kt
 │   │   ├─ ThanksActivity.kt
 │   │   ├─ adapter/FoodAdapter.kt
 │   │   └─ model/Food.kt
 │   ├─ res/
 │   │   ├─ layout/
 │   │   │   ├─ activity_main.xml
 │   │   │   ├─ activity_signin.xml
 │   │   │   ├─ activity_login.xml
 │   │   │   ├─ activity_register.xml
 │   │   │   ├─ activity_dashboard.xml
 │   │   │   ├─ item_food.xml
 │   │   │   ├─ activity_order.xml
 │   │   │   ├─ activity_confirm_order.xml
 │   │   │   └─ activity_thanks.xml
 │   │   ├─ drawable/ (logoapp, food1..food10, icons...)
 │   │   ├─ anim/ (fade_slide_in.xml, pop_in.xml, modal_slide_up.xml, modal_fade_out.xml ...)
 │   │   ├─ values/colors.xml
 │   │   ├─ values/themes.xml
 │   │   └─ font/ (poppins_*.ttf)

Penjelasan komponen & logic penting (detail teknis — untuk dosen)

Saya jelaskan tiap bagian kunci agar mudah dipahami.

1. LoginActivity

Fungsi: otentikasi sederhana menggunakan dummyUsers map.

Sesi: setelah login sukses, SharedPreferences user_session diisi:

sharedPrefs.edit().apply {
    putBoolean("isLoggedIn", true)
    putString("username", username)
    apply()
}


Auto-redirect: saat onCreate() dicek isLoggedIn → jika true langsung startActivity(DashboardActivity) lalu finish().

Catatan: Untuk development sempat ada opsi reset session agar tidak auto-login terus; bisa dikontrol.

2. RegisterActivity

Validasi sederhana:

Semua field wajib,

Email mengandung @,

Username harus ada huruf besar (contoh aturan untuk latihan),

Password & konfirmasi harus sama.

Setelah register: redirect ke LoginActivity (no persistence).

3. DashboardActivity & FoodAdapter

Data: foodList berisi 10 Food(name, description, imageResId) hardcoded.

RecyclerView:

GridLayoutManager(2) default; toggle ke LinearLayoutManager untuk list.

Toggle disimpan di isGridLayout (saat ini di-memory; bisa di-persist jika dibutuhkan).

FoodAdapter (pattern yang baik):

class FoodAdapter(
    private val foodList: List<Food>,
    private val onAddToCart: (Food) -> Unit,
    private val onOrderNow: (Food) -> Unit
) : RecyclerView.Adapter<...>


onAddToCart(food) dipanggil ketika user klik tombol tambah ke keranjang.

onOrderNow(food) dipanggil ketika user klik order now; DashboardActivity menanganinya dengan Intent ke OrderActivity:

val intent = Intent(this, OrderActivity::class.java)
intent.putExtra("FOOD_NAME", selectedFood.name)
startActivity(intent)

4. OrderActivity

Tugas: tampilkan username dari SharedPreferences dan FOOD_NAME dari Intent.

UI: card + scrollable details + tombol lanjut. Ini modal-like feel tapi implementasinya adalah Activity.

5. ConfirmOrderActivity

Form inputs: etFullName, etAddress, etLandmark.

Validasi: minimal — nama & alamat wajib.

Jika valid: kirim Intent ke ThanksActivity dengan extras:

intent.putExtra("FULL_NAME", fullName)
intent.putExtra("ADDRESS", address)
intent.putExtra("LANDMARK", landmark)
intent.putExtra("FOOD_NAME", foodName)


Design: menggunakan MaterialCardView di dalam ConstraintLayout, TextInputLayout untuk form (outline style).

6. ThanksActivity (Modal)

Tema: activity menggunakan Theme.Snacc.Dialog (dialog-style) sehingga tampil seperti modal (card di tengah, background dim).

Animasi: modal_slide_up (enter), modal_fade_out (exit) — membuat masuk dari bawah + fade.

Blur effect:

Jika device Android 12+ (API >= 31): gunakan RenderEffect.createBlurEffect(radiusX, radiusY, TileMode) di window.decorView untuk blur background.

Jika device lama: fallback mengatur alpha semi-transparan pada background (tidak crash).

Penutupan: tombol Tutup menutup activity dengan fade-out.

Desain & Tema

Warna utama (res/values/colors.xml):

primaryColor: #1B1B1D (gelap)

secondaryColor: #5C3D77 (ungu)

accentColor: #8B5E3C dll.

Font: Poppins family (multi-weight) di res/font/.

Animasi: fade_slide_in, pop_in, modal_slide_up, modal_fade_out.

Tujuan desain: modern, minimal, kontras warna konsisten, card-centric UI.

Cara Build & Run (untuk dosen / penguji)

Buka project di Android Studio (Electric Eel ke atas direkomendasikan).

Pastikan minSdkVersion sesuai (project bekerja di device API 21+, blur native di API 31+).

Sync Gradle → build → run di emulator atau device.

Langkah uji manual:

Jalankan app → klik Start now → Signin → Login menggunakan dummy account.

Di Dashboard: toggle layout (grid/list), coba tombol Add to Cart (Toast) dan Order Now (membuka OrderActivity).

Lanjut ke ConfirmOrder → isi form → kirim → lihat modal Thanks.

Hal-hal teknis & keputusan desain (alasan, trade-offs)

SharedPreferences untuk session: mudah dan cukup untuk demo; bukan solusi aman untuk produk produksi. Untuk produksi gunakan token-based auth (JWT) + backend.

Dummy data: memudahkan presentasi tanpa backend. Untuk integrasi nyata gunakan Supabase / Firebase / REST API (komentar di code menunjukkan titik integrasi).

Blur menggunakan RenderEffect: sangat bagus di Android 12+, tapi tidak tersedia di versi lama → fallback semi-transparan dipilih demi kompatibilitas.

Adapter menggunakan callback: memisahkan UI item dari logic activity — pola ini memudahkan testing & reuse.

Animasi: meningkatkan persepsi kualitas UX; tidak berlebihan agar tetap ringan.

Hal yang bisa ditingkatkan (future work / saran tugas lanjutan)

Simpan preferensi layout (isGridLayout) di SharedPreferences supaya persist antar sesi.

Implementasi keranjang (CartActivity) menyimpan list item dan total harga.

Integrasi Supabase / Firebase untuk autentikasi & penyimpanan pesanan.

Unit tests & UI tests (Espresso) untuk flow login → order → confirm → thanks.

Resource loading: gunakan Glide/Picasso untuk gambar tinggi resolusi agar hemat memori.

Localisation (i18n) jika butuh multi-bahasa.

Accessibility: fokus pada contrast ratio, content descriptions, talkback.

Contoh kode penting (ringkasan)

Menyimpan session saat login

sharedPrefs.edit().apply {
    putBoolean("isLoggedIn", true)
    putString("username", username)
    apply()
}


Mengambil username di activity lain

val prefs = getSharedPreferences("user_session", MODE_PRIVATE)
val username = prefs.getString("username", "Pengguna")


Intent dari Dashboard → Order

val intent = Intent(this, OrderActivity::class.java)
intent.putExtra("FOOD_NAME", selectedFood.name)
startActivity(intent)


Blur conditional (Android 12+)

if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
    window.decorView.setRenderEffect(
        RenderEffect.createBlurEffect(25f, 25f, Shader.TileMode.CLAMP)
    )
} else {
    binding.mainThanks.background.alpha = 200 // fallback
}

Cara menjelaskan ke dosen (script singkat)

Kalau lo presentasi, pakai alur berikut:

Tujuan: tunjukkan fitur UI/UX + flow end-to-end tanpa backend.

Demo: login (pakai akun dummy) → tunjuk dashboard (grid/list toggle) → order item → isi alamat → confirm → tampil thanks modal.

Teknis: jelaskan SharedPreferences untuk session, RecyclerView + Adapter pattern dengan callback, penggunaan Intent untuk passing data antar activity, dan conditional blur.

Keamanan & Scalability: sebut kekurangan (dummy auth, penyimpanan lokal) dan rencana perbaikan (backend & token).

Q&A: siap jelaskan potongan kode apa pun; fokus pada FoodAdapter, LoginActivity session, dan ConfirmOrderActivity validation.

Lisensi & Credits

Repositori ini bersifat contoh akademik. Gunakan semestinya untuk tugas UAS/praktikum. Berikan kredit bila memakai kembali.
