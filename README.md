\# Plants vs Zombies — Java Edition



Game Plants vs Zombies được xây dựng bằng Java Swing.



\## Cách chạy



\### Yêu cầu

\- Java 17 trở lên



\### Chạy từ JAR

```

java -jar Project.jar

```



\### Chạy từ source

Mở project bằng IntelliJ IDEA và chạy file `Main.java`



\## Cách chơi



| Phím | Chức năng |

|---|---|

| 1 - 6 | Chọn cây |

| Click lưới | Đặt cây |

| Click sun | Thu thập mặt trời |

| ESC | Pause / Save game |



\## Cây



| Cây | Chi phí | Chức năng |

|---|---|---|

| PeaShooter | 100 ☀ | Bắn đạn thẳng |

| Sunflower | 50 ☀ | Tạo mặt trời |

| WallNut | 50 ☀ | Chặn zombie |

| SnowPea | 175 ☀ | Đạn băng làm chậm zombie |

| CherryBomb | 150 ☀ | Nổ diệt vùng 3×3 |

| PotatoMine | 25 ☀ | Bẫy nổ dưới đất |



\## Zombie



| Zombie | Đặc điểm |

|---|---|

| BasicZombie | Zombie thường |

| BucketZombie | HP cao, xuất hiện từ wave 2 |

| PoleVaultZombie | Nhảy qua cây đầu tiên, wave 3 |



\## Design Patterns sử dụng



\- \*\*State Pattern\*\* — quản lý màn hình Menu / Playing / GameOver

\- \*\*Factory Method\*\* — tạo entity Plant và Zombie



\## Cấu trúc project



```

src/com/pvz/

├── core/       # Game loop, State machine

├── entity/     # Plant, Zombie, Projectile, Effect

├── level/      # GameWorld, EffectManager

├── ui/         # HUD

└── util/       # SaveManager

```

