from sympy import symbols, diff, solve, Matrix

# Değişkenleri tanımla
x1, x2 = symbols('x1 x2')

# Fonksiyonu tanımla
f = (x1 - 1)**2 + (x2 - 1)**2 - x1*x2

# Kısmi türevleri al
df_dx1 = diff(f, x1)
df_dx2 = diff(f, x2)

# Hessian matrisini oluştur
H = Matrix([[diff(df_dx1, x1), diff(df_dx1, x2)],
            [diff(df_dx2, x1), diff(df_dx2, x2)]])

# Durağan noktaları bul
stationary_points = solve((df_dx1, df_dx2), (x1, x2))

# Her bir durağan noktayı kontrol et
for point in stationary_points:
    x1_val = point[x1]
    x2_val = point[x2]
    H_val = H.subs({x1: x1_val, x2: x2_val})
    det_H = H_val.det()
    tr_H = H_val.trace()

    if det_H > 0 and tr_H > 0:
        print(f"Minimum nokta: ({x1_val}, {x2_val})")
        print(f"Fonksiyonun minimum değeri: {f.subs({x1: x1_val, x2: x2_val})}\n")
