import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', component: AppComponent },
  
  { path: 'businessSpace',loadChildren: () => import('src/app/_Business/Business_layouts/Courzelo_businessHome/courzelo-business-home.module').then((m)=>m.CourzeloBusinessHomeModule ) },
 
  { path: '', redirectTo: '', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
