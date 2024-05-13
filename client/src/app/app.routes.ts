import { Routes } from '@angular/router';
import {HomeComponent} from "./components/home/home.component";

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'search/:search', component: HomeComponent },
  { path: 'author/:author', component: HomeComponent },
  { path: 'filter/:filter', component: HomeComponent },

];
