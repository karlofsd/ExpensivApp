package core.constants;

import core.enums.*;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Scanner;

public class Options {
    public final EnumMap<MenuOpt,String> MENU = new EnumMap<>(MenuOpt.class);
    public final EnumMap<AccountOpt,String> ACCOUNT = new EnumMap<>(AccountOpt.class);
    public final EnumMap<CategoryOpt,String> CATEGORY = new EnumMap<>(CategoryOpt.class);
    public final EnumMap<SpotOpt,String> SPOT = new EnumMap<>(SpotOpt.class);
    public final EnumMap<HistoryOpt,String> HISTORY = new EnumMap<>(HistoryOpt.class);

    public Options() {
        MENU.put(MenuOpt.ACCOUNTS,"Cuentas");
        MENU.put(MenuOpt.CATEGORIES,"Categories");
        ACCOUNT.put(AccountOpt.CREATE,"Crear");
        ACCOUNT.put(AccountOpt.DELETE,"Borrar");
        ACCOUNT.put(AccountOpt.SELECT,"Seleccionar");
        CATEGORY.put(CategoryOpt.CREATE,"Crear");
        CATEGORY.put(CategoryOpt.DELETE,"Borrar");
        SPOT.put(SpotOpt.DEPOSIT,"Depositar");
        SPOT.put(SpotOpt.DRAW,"Retirar");
        SPOT.put(SpotOpt.HISTORY,"Historial");
        HISTORY.put(HistoryOpt.GET_ALL,"Obtener todas");
        HISTORY.put(HistoryOpt.GET_TRANSACTION,"Buscar transacción");
        HISTORY.put(HistoryOpt.GET_BY_DAY,"Obtener por día");
        HISTORY.put(HistoryOpt.GET_BY_WEEK,"Obtener por semana");
        HISTORY.put(HistoryOpt.GET_BY_MONTH,"Obtener por mes");
    }
}
