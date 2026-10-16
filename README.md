# Java Academy — portfolio + tələbə platforması

Spring Boot 4 · Java 21 · Thymeleaf · PostgreSQL · Flyway · Railway

## Lokal işə salmaq

Docker açıq olmalıdır (PostgreSQL `compose.yaml` ilə avtomatik qalxır).

```bash
SPRING_PROFILES_ACTIVE=dev ./gradlew bootRun
```

- Sayt: http://localhost:8080
- Admin: `admin@local.dev` / `admin12345` (yalnız `dev` profilində)
- Testlər: `./gradlew test`

## Nə harada dəyişdirilir

| Nə | Harada |
|---|---|
| Ad, bio, iş təcrübəsi, rəqəmlər | `src/main/resources/messages_az.properties`, `messages_ru.properties`, `messages.properties` (EN) → `profile.*` |
| Bacarıqlar (chip-lər) | `templates/home.html` → "Skills" bölməsi |
| Kurs təsvirləri | `messages*.properties` → `course.java-essentials.*`, `course.java-advanced.*` |
| Ay adları | Admin → Kurslar → ay → "redaktə et" |
| Dərslər, fayllar, video | Admin → Kurslar → ay → "+ Yeni dərs" |
| Brend adı, əlaqə linkləri | Environment variables (aşağıda) |

## Biznes qaydaları

- **Ziyarətçi**: portfolio, kurs proqramı, bloq və "Pulsuz" işarəli dərsləri görür.
- **Tələbə**: yalnız admin yaradır. İlk girişdə şifrəni dəyişməlidir.
- **Aylar bir-bir açılır**: Ay N = başlama tarixi + (N−1) ay (Bakı vaxtı ilə).
- **Java Advanced** tələbəsi bütün **Java Essentials** materiallarını tam görür.
- Bitmə tarixi keçəndə və ya qeydiyyat deaktiv ediləndə giriş avtomatik bağlanır.
- Hesab deaktiv ediləndə tələbə dərhal sistemdən çıxarılır.
- Dərs faylları heç vaxt birbaşa link ilə açılmır, hər yükləmədə giriş yoxlanılır.
- 5 uğursuz login cəhdindən sonra IP 15 dəqiqəlik bloklanır.

## Railway-ə deploy

1. Kodu GitHub-a push edin.
2. Railway → **New Project** → **Deploy from GitHub repo** (Dockerfile avtomatik tapılır).
3. Layihəyə **+ New → Database → PostgreSQL** əlavə edin.
4. Tətbiq servisinə **Volume** əlavə edin, mount path: `/data` (yüklənən şəkillər/fayllar üçün).
5. Tətbiq servisinin **Variables** bölməsi:

```
PGHOST=${{Postgres.PGHOST}}
PGPORT=${{Postgres.PGPORT}}
PGDATABASE=${{Postgres.PGDATABASE}}
PGUSER=${{Postgres.PGUSER}}
PGPASSWORD=${{Postgres.PGPASSWORD}}
ADMIN_EMAIL=sizin@email.com
ADMIN_PASSWORD=<güclü-şifrə>
APP_BASE_URL=https://sizin-domen.az
APP_BRAND=Java Academy
APP_OWNER=Adınız Soyadınız
CONTACT_EMAIL=...
CONTACT_PHONE=+994...
CONTACT_LINKEDIN=https://linkedin.com/in/...
CONTACT_GITHUB=https://github.com/...
CONTACT_TELEGRAM=https://t.me/...
```

İstəyə görə, yeni müraciət gələndə email bildirişi üçün: `MAIL_HOST`, `MAIL_PORT`, `MAIL_USERNAME`, `MAIL_PASSWORD`, `NOTIFY_EMAIL`.

6. **Settings → Networking → Custom Domain** ilə domeninizi qoşun (HTTPS avtomatikdir).
7. Admin hesabı ilk işə salınmada `ADMIN_EMAIL`/`ADMIN_PASSWORD` ilə yaradılır.

Backup: Railway Postgres → **Backups** bölməsində avtomatik backup-ları aktiv edin.
