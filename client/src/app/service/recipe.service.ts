import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RecipeDto} from "../dto/recipe.dto";

const BASE_URL = "http://localhost:8080/api/recipe/";

@Injectable({
  providedIn: 'root'
})
export class RecipeService {
  private httpClient = inject(HttpClient);

  getRecipe(pageNumber: number) {
    return this.httpClient.get<RecipeDto[]>(BASE_URL + "page/" + pageNumber);
  }

  getNumberPages() {
    return this.httpClient.get<number>(BASE_URL + "number_pages");
  }
}
