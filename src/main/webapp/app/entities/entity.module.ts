import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'escuela',
        loadChildren: () => import('./escuela/escuela.module').then(m => m.SiieEscuelaModule)
      },
      {
        path: 'ciclo',
        loadChildren: () => import('./ciclo/ciclo.module').then(m => m.SiieCicloModule)
      },
      {
        path: 'subciclo',
        loadChildren: () => import('./subciclo/subciclo.module').then(m => m.SiieSubcicloModule)
      },
      {
        path: 'tipoperiodo',
        loadChildren: () => import('./tipoperiodo/tipoperiodo.module').then(m => m.SiieTipoperiodoModule)
      },
      {
        path: 'escolaridad',
        loadChildren: () => import('./escolaridad/escolaridad.module').then(m => m.SiieEscolaridadModule)
      },
      {
        path: 'persona',
        loadChildren: () => import('./persona/persona.module').then(m => m.SiiePersonaModule)
      },
      {
        path: 'jornada',
        loadChildren: () => import('./jornada/jornada.module').then(m => m.SiieJornadaModule)
      },
      {
        path: 'financiamiento',
        loadChildren: () => import('./financiamiento/financiamiento.module').then(m => m.SiieFinanciamientoModule)
      },
      {
        path: 'funcionlaboral',
        loadChildren: () => import('./funcionlaboral/funcionlaboral.module').then(m => m.SiieFuncionlaboralModule)
      },
      {
        path: 'motivomov',
        loadChildren: () => import('./motivomov/motivomov.module').then(m => m.SiieMotivomovModule)
      },
      {
        path: 'tipomov',
        loadChildren: () => import('./tipomov/tipomov.module').then(m => m.SiieTipomovModule)
      },
      {
        path: 'partida',
        loadChildren: () => import('./partida/partida.module').then(m => m.SiiePartidaModule)
      },
      {
        path: 'categoria',
        loadChildren: () => import('./categoria/categoria.module').then(m => m.SiieCategoriaModule)
      },
      {
        path: 'plaza',
        loadChildren: () => import('./plaza/plaza.module').then(m => m.SiiePlazaModule)
      },
      {
        path: 'horariolab',
        loadChildren: () => import('./horariolab/horariolab.module').then(m => m.SiieHorariolabModule)
      },
      {
        path: 'direccion',
        loadChildren: () => import('./direccion/direccion.module').then(m => m.SiieDireccionModule)
      },
      {
        path: 'usuario',
        loadChildren: () => import('./usuario/usuario.module').then(m => m.SiieUsuarioModule)
      },
      {
        path: 'rol',
        loadChildren: () => import('./rol/rol.module').then(m => m.SiieRolModule)
      },
      {
        path: 'modulo',
        loadChildren: () => import('./modulo/modulo.module').then(m => m.SiieModuloModule)
      },
      {
        path: 'permiso',
        loadChildren: () => import('./permiso/permiso.module').then(m => m.SiiePermisoModule)
      },
      {
        path: 'funcion',
        loadChildren: () => import('./funcion/funcion.module').then(m => m.SiieFuncionModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SiieEntityModule {}
