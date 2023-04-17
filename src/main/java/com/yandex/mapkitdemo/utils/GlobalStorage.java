package com.yandex.mapkitdemo.utils;

import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkitdemo.R;
import com.yandex.mapkitdemo.utils.model.MyTime;
import com.yandex.mapkitdemo.utils.model.StopTrans;
import com.yandex.mapkitdemo.utils.model.TransportPoint;
import com.yandex.mapkitdemo.utils.model.ViewTransportData;

import java.util.ArrayList;
import java.util.List;

public class GlobalStorage {

    public static List<String> notifications = new ArrayList<>();
    public static boolean mapState = false;
    public static List<ViewTransportData> transportData;


    public static void initTransportSchedulers() {

        List<MyTime> stopTimeArrival = new ArrayList<>();
        stopTimeArrival.add(new MyTime(5, 29));
        stopTimeArrival.add(new MyTime(5, 40));
        stopTimeArrival.add(new MyTime(5, 51));
        stopTimeArrival.add(new MyTime(7, 7));
        stopTimeArrival.add(new MyTime(7, 19));
        stopTimeArrival.add(new MyTime(7, 31));
        stopTimeArrival.add(new MyTime(8, 4));
        stopTimeArrival.add(new MyTime(8, 15));
        stopTimeArrival.add(new MyTime(8, 26));
        stopTimeArrival.add(new MyTime(9, 10));
        stopTimeArrival.add(new MyTime(9, 21));
        stopTimeArrival.add(new MyTime(9, 36));
        stopTimeArrival.add(new MyTime(10, 6));
        stopTimeArrival.add(new MyTime(10, 21));
        stopTimeArrival.add(new MyTime(10, 36));
        stopTimeArrival.add(new MyTime(11, 6));
        stopTimeArrival.add(new MyTime(11, 21));
        stopTimeArrival.add(new MyTime(11, 36));
        stopTimeArrival.add(new MyTime(12, 9));
        stopTimeArrival.add(new MyTime(12, 20));
        stopTimeArrival.add(new MyTime(12, 30));
        stopTimeArrival.add(new MyTime(13, 3));
        stopTimeArrival.add(new MyTime(13, 15));
        stopTimeArrival.add(new MyTime(13, 26));
        stopTimeArrival.add(new MyTime(14, 8));
        stopTimeArrival.add(new MyTime(14, 21));
        stopTimeArrival.add(new MyTime(14, 34));
        stopTimeArrival.add(new MyTime(15, 9));
        stopTimeArrival.add(new MyTime(15, 20));
        stopTimeArrival.add(new MyTime(15, 31));
        stopTimeArrival.add(new MyTime(16, 7));
        stopTimeArrival.add(new MyTime(16, 19));
        stopTimeArrival.add(new MyTime(16, 30));
        stopTimeArrival.add(new MyTime(17, 4));
        stopTimeArrival.add(new MyTime(17, 16));
        stopTimeArrival.add(new MyTime(17, 28));
        stopTimeArrival.add(new MyTime(18, 4));
        stopTimeArrival.add(new MyTime(18, 16));
        stopTimeArrival.add(new MyTime(18, 26));
        stopTimeArrival.add(new MyTime(19, 14));
        stopTimeArrival.add(new MyTime(19, 29));
        stopTimeArrival.add(new MyTime(19, 44));
        stopTimeArrival.add(new MyTime(20, 13));
        stopTimeArrival.add(new MyTime(20, 27));
        stopTimeArrival.add(new MyTime(20, 40));
        stopTimeArrival.add(new MyTime(21, 8));
        stopTimeArrival.add(new MyTime(21, 23));
        stopTimeArrival.add(new MyTime(21, 40));
        stopTimeArrival.add(new MyTime(22, 10));
        stopTimeArrival.add(new MyTime(22, 26));
        stopTimeArrival.add(new MyTime(22, 42));
        stopTimeArrival.add(new MyTime(23, 15));
        stopTimeArrival.add(new MyTime(23, 32));
        stopTimeArrival.add(new MyTime(23, 49));
        List<StopTrans> stopTrans = new ArrayList<>();
        stopTrans.add(new StopTrans("ДС Карастояновой", new Point(53.940097, 27.564839), stopTimeArrival));
        stopTrans.add(new StopTrans("Карастояновой", new Point(53.938490, 27.565803), stopTimeArrival));
        stopTrans.add(new StopTrans("Орловская", new Point(53.933725, 27.565690), stopTimeArrival));
        stopTrans.add(new StopTrans("Кульман", new Point(53.927677, 27.566653), stopTimeArrival));
        stopTrans.add(new StopTrans("Парк Дружбы Народов", new Point(53.925690, 27.570927), stopTimeArrival));
        stopTrans.add(new StopTrans("Максима Богдановича", new Point(53.920925, 27.568692), stopTimeArrival));
        stopTrans.add(new StopTrans("Комаровский рынок", new Point(53.919049, 27.575927), stopTimeArrival));
        stopTrans.add(new StopTrans("Станция метро Площадь Якуба Коласа", new Point(53.916317, 27.581092), stopTimeArrival));
        stopTrans.add(new StopTrans("Красная", new Point(53.912995, 27.574949), stopTimeArrival));
        stopTrans.add(new StopTrans("Проспект Независимости", new Point(53.910148, 27.578275), stopTimeArrival));
        stopTrans.add(new StopTrans("Берестянская", new Point(53.906848, 27.588117), stopTimeArrival));
        stopTrans.add(new StopTrans("Платонова", new Point(53.905707, 27.591880), stopTimeArrival));
        stopTrans.add(new StopTrans("Гипсовый завод", new Point(53.9037710669086, 27.59876638313463), stopTimeArrival));
        stopTrans.add(new StopTrans("Переулок Козлова", new Point(53.900942, 27.602004), stopTimeArrival));
        stopTrans.add(new StopTrans("Уральская", new Point(53.898637, 27.607077), stopTimeArrival));
        stopTrans.add(new StopTrans("Фроликова", new Point(53.901266, 27.613793), stopTimeArrival));
        stopTrans.add(new StopTrans("Менделеева", new Point(53.902446, 27.618904), stopTimeArrival));
        stopTrans.add(new StopTrans("Бумажкова", new Point(53.902601, 27.624196), stopTimeArrival));
        stopTrans.add(new StopTrans("Аннаева", new Point(53.904053, 27.628153), stopTimeArrival));
        stopTrans.add(new StopTrans("Запорожская площадь", new Point(53.905022, 27.632943), stopTimeArrival));
        stopTrans.add(new StopTrans("Слепянка", new Point(53.905875, 27.637979), stopTimeArrival));

        List<TransportPoint> points = new ArrayList<>();
        points.add(new TransportPoint(stopTrans));
        points.forEach(TransportPoint::fillPoints);

        transportData = new ArrayList<>();
        transportData.add(new ViewTransportData(19, points, R.drawable.bus, "ДС Карастояновой-Слепянка"));
        transportData.add(new ViewTransportData(29, null, R.drawable.bus, "Карастояновой- ДС Кунцевщина"));
        transportData.add(new ViewTransportData(31, null, R.drawable.bus, "Карастояновой-ДС Кунцевщина"));
        transportData.add(new ViewTransportData(33, null, R.drawable.bus, "Уручье-2-2-е ворота"));
        transportData.add(new ViewTransportData(34, null, R.drawable.bus, "Копище-Станция метро Уручье"));
        transportData.add(new ViewTransportData(35, null, R.drawable.bus, "Славинского-Переулок Липковский"));
        transportData.add(new ViewTransportData(36, null, R.drawable.bus, "Кунцевщина-Тарасово"));
        transportData.add(new ViewTransportData(37, null, R.drawable.bus, "Карбышева - ДС Восточная"));
        transportData.add(new ViewTransportData(38, null, R.drawable.bus, "Карастояновой-РКБобруйская"));
        transportData.add(new ViewTransportData(39, null, R.drawable.bus, "Восточная-Щедрина"));
        transportData.add(new ViewTransportData(40, null, R.drawable.bus, "Кунцевщина-Ландера"));
        transportData.add(new ViewTransportData(41, null, R.drawable.bus, "Одоевского-Каменная Горка-5"));
        transportData.add(new ViewTransportData(42, null, R.drawable.bus, "Сухарево-5-Люцинская"));
        transportData.add(new ViewTransportData(140, null, R.drawable.bus, "Сухарево-5-ТЦ Ждановичи"));

        transportData.add(new ViewTransportData(1, null, R.drawable.trolleybus, "Зелёный Луг-7-Станция метро Московская"));
        transportData.add(new ViewTransportData(2, null, R.drawable.trolleybus, "Уручье-2-ДС Зелёный Луг-7"));
        transportData.add(new ViewTransportData(3, null, R.drawable.trolleybus, "Ангарская-4-Вокзал"));
        transportData.add(new ViewTransportData(4, null, R.drawable.trolleybus, "Одоевского-РК Бобруйская"));
        transportData.add(new ViewTransportData(6, null, R.drawable.trolleybus, "Лошица-2-Вокзал"));
        transportData.add(new ViewTransportData(7, null, R.drawable.trolleybus, "Сухарево-5-РК Бобруйская"));
        transportData.add(new ViewTransportData(8, null, R.drawable.trolleybus, "Сухарево-5-ДС Дружная"));
        transportData.add(new ViewTransportData(9, null, R.drawable.trolleybus, "Кунцевщина-Романовская Слобода"));
        transportData.add(new ViewTransportData(10, null, R.drawable.trolleybus, "Веснянка-ДС Малиновка-4"));
        transportData.add(new ViewTransportData(25, null, R.drawable.trolleybus, "Кунцевщина- ДС Малиновка-4"));

        transportData.add(new ViewTransportData(1, null, R.drawable.tramway, "Зелёный Луг-Площадь Мясникова"));
        transportData.add(new ViewTransportData(3, null, R.drawable.tramway, "Озеро-ДС Серебрянка"));
        transportData.add(new ViewTransportData(4, null, R.drawable.tramway, "Озеро-Площадь Мясникова"));
        transportData.add(new ViewTransportData(5, null, R.drawable.tramway, "Озеро-ДС Зелёный Луг"));
        transportData.add(new ViewTransportData(6, null, R.drawable.tramway, "'Серебрянка-ДС Зелёный Луг"));
        transportData.add(new ViewTransportData(7, null, R.drawable.tramway, "Серебрянка-Площадь Мясникова"));
        transportData.add(new ViewTransportData(9, null, R.drawable.tramway, "Серебрянка-Тракторный завод"));
        transportData.add(new ViewTransportData(11, null, R.drawable.tramway, "Зелёный Луг-РК Змитрока Бядули"));
        transportData.forEach(ViewTransportData::fillStopsList);

    }


}
