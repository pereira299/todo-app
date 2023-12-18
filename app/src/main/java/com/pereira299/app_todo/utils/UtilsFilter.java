package com.pereira299.app_todo.utils;

import com.pereira299.app_todo.entities.Compromisso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UtilsFilter {
    List<Compromisso> list;

    public UtilsFilter(List<Compromisso> list) {
        this.list = list;
    }

    public UtilsFilter dateFilter(Date date){
        if(date == null) throw new NullPointerException();
        if(list.size() == 0) return this;
        ArrayList<Compromisso> result = new ArrayList<>();
        for (Compromisso compromisso : list){
            if(compromisso.getDataHora().equals(date)){
                result.add(compromisso);
            }
        }

        list = result;
        return this;
    }

    public UtilsFilter dateFilter(Date date, DateFilterType type){
        if(list.size() == 0) return this;
        if(date == null) throw new NullPointerException();
        if(type == DateFilterType.EQUAL) return dateFilter(date);

        ArrayList<Compromisso> result = new ArrayList<>();
        for (Compromisso compromisso : list){
            Date dataHora = compromisso.getDataHora();
            switch (type){
                case BEFORE:
                    if(dataHora.before(date)){
                        result.add(compromisso);
                    }
                    break;
                case AFTER:
                    if(dataHora.after(date)){
                        result.add(compromisso);
                    }
                    break;

            }
        }
        list = result;
        return this;
    }


    public UtilsFilter dateFilter(Date date1, Date date2){
        if(list.size() == 0) return this;
        if(date2 == null || date1 == null) throw new NullPointerException();
        ArrayList<Compromisso> result = new ArrayList<>();
        for (Compromisso compromisso : list){
            Date dataHora = compromisso.getDataHora();
            if(dataHora.before(date1) && dataHora.after(date2)){
                result.add(compromisso);
            }
        }
        list = result;
        return this;
    }

    public UtilsFilter textFilter(String text){
        if(list.size() == 0) return this;
        if(text == null) throw new NullPointerException();
        ArrayList<Compromisso> result = new ArrayList<>();
        for (Compromisso compromisso : list){
            if (compromisso.getTitulo().contains(text)){
                result.add(compromisso);
            }
        }
        list = result;
        return this;
    }

    public UtilsFilter recebidoFilter(){
        if(list.size() == 0) return this;
        ArrayList<Compromisso> result = new ArrayList<>();
        for (Compromisso compromisso : list){
            if(compromisso.isRecebido()){
                result.add(compromisso);
            }
        }
        list = result;
        return this;
    }

    public UtilsFilter finalizadoFilter(){
        if(list.size() == 0) return this;
        ArrayList<Compromisso> result = new ArrayList<>();
        for (Compromisso compromisso : list){
            if(compromisso.isExecutado()){
                result.add(compromisso);
            }
        }
        list = result;
        return this;
    }

    public List<Compromisso> getResult(){
        return list;
    }
}
