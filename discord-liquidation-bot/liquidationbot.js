const math = require('mathjs');

// Değişkenleri tanımla
const x1 = math.symbol('x1');
const x2 = math.symbol('x2');

// Fonksiyonu tanımla
const f = math.parse('(x1 - 1)^2 + (x2 - 1)^2 - x1 * x2');

// Kısmi türevleri al
const df_dx1 = math.derivative(f, x1);
const df_dx2 = math.derivative(f, x2);

// Hesse matrisini oluştur
const H = math.matrix([
    [math.derivative(df_dx1, x1), math.derivative(df_dx1, x2)],
    [math.derivative(df_dx2, x1), math.derivative(df_dx2, x2)]
]);

// Durağan noktaları bul
const stationary_points = math.solve([df_dx1, df_dx2], [x1, x2]);

// Her bir durağan noktayı kontrol et
stationary_points.forEach(point => {
    const x1_val = point.get(x1);
    const x2_val = point.get(x2);
    const H_val = math.subs(H, {x1: x1_val, x2: x2_val});
    const det_H = math.det(H_val);
    const tr_H = math.trace(H_val);

    if (det_H > 0 && tr_H > 0) {
        console.log(`Minimum nokta: (${x1_val}, ${x2_val})`);
        console.log(`Fonksiyonun minimum değeri: ${math.subs(f, {x1: x1_val, x2: x2_val})}\n`);
    }
});