package us.lsi.ag.fiesta;

import java.util.List;

import com.google.common.collect.Lists;

import us.lsi.ag.ProblemaAGBinary;
import us.lsi.ag.agchromosomes.BinaryChromosome;
import us.lsi.common.Tuple2;

public class ProblemaFiestaAGBinary implements
		ProblemaAGBinary<List<Actividad>> {

	@Override
	public int getDimensionDelChromosoma() {
		return ProblemaFiesta.actividadesDisponibles.size();
	}

	@Override
	public Double fitnessFunction(BinaryChromosome cr) {
		List<Integer> list = cr.decode();
		Double valoracionTotal = 0.0;
		Double costeTotal = 0.0;
		List<Actividad> actividadesARealizar = Lists.newArrayList();
		// Vemos presupuesto y valoracion
		Double valoracion = 0.;
		for (int i = 0; i < list.size(); i++) {
			Actividad a = ProblemaFiesta.getActividad(i);
			if (list.get(i) > 0){
				valoracion += a.getValoracion();
				costeTotal += a.getCoste();
				actividadesARealizar.add(a);
			}
		}
		Integer numActIncomp = 0;
		for (Tuple2<Actividad, Actividad> p : ProblemaFiesta.restricciones) {
			if (list.get(ProblemaFiesta.getPos(p.v1))>0  && list.get(ProblemaFiesta.getPos(p.v2))>0) {
				numActIncomp++;
			}
		}

		Double presRest = ProblemaFiesta.presupuestoTotal-costeTotal;
		presRest = presRest>=0. ? 0.0: presRest;
		Double d = numActIncomp * numActIncomp + presRest * presRest;
		Double f = valoracionTotal - 10000000L * d;
		return f;
	}

	@Override
	public List<Actividad> getSolucion(BinaryChromosome cr) {
		List<Actividad> actividades = Lists.newArrayList();
		List<Integer> ls = cr.decode();
		for (int i = 0; i < ls.size(); i++) {
			if (ls.get(i) > 0) {
				actividades.add(ProblemaFiesta.getActividad(i));
			}
		}
		return actividades;

	}

}
