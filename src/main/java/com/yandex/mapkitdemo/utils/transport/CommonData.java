package com.yandex.mapkitdemo.utils.transport;

import com.yandex.mapkitdemo.R;

import java.util.ArrayList;
import java.util.List;

public class CommonData {

    List<Trans> trans;

    public void init(){
        trans = new ArrayList<>();
        ArrayList<StopsData> stops = new ArrayList<>();
        stops.add(new StopsData("Красный Бор"));
        stops.add(new StopsData("Красный Бор"));
        stops.add(new StopsData("Академия Вышелесского"));
        stops.add(new StopsData("Нёманская"));
        stops.add(new StopsData("Станция метро \"Каменная Горка\""));
        stops.add(new StopsData("Запад-3"));
        stops.add(new StopsData("Мазурова"));
        stops.add(new StopsData("Микрорайон Сухарево-3"));
        stops.add(new StopsData("Чайлытко"));
        stops.add(new StopsData("Максима Горецкого"));
        stops.add(new StopsData("Янковского"));
        stops.add(new StopsData("Рафиева"));
        stops.add(new StopsData("Проспект Газеты Звязда"));
        stops.add(new StopsData("Алибегова"));
        stops.add(new StopsData("Маждународный Образовательный Центр"));
        stops.add(new StopsData("ТЦ \"Кримаш\""));
        stops.add(new StopsData("Универсам Волгоград"));
        stops.add(new StopsData("Станция Метро Малиновка"));
        stops.add(new StopsData("Поворот на филиал БГУ"));
        stops.add(new StopsData("Ландера"));
        stops.add(new StopsData("Гимназия"));
        stops.add(new StopsData("Кинотеатр Электрон"));
        stops.add(new StopsData("Завод Транзистор"));
        trans.add(new Trans("30c",R.drawable.bus,stops));

    }
    public void getByNumber(){

    }

    public void findByStreet(){

    }


}