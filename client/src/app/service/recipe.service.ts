import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RecipeDto} from "../dto/recipe.dto";
import {RecipeDetailsDto} from "../dto/recipe-details.dto";

const BASE_URL = "http://localhost:8080/api/recipe/";

@Injectable({
  providedIn: 'root'
})
export class RecipeService {
  private httpClient = inject(HttpClient);

  getRecipe(pageNumber: number) {
    return this.httpClient.get<RecipeDto[]>(BASE_URL + "page/" + pageNumber);
  }

  getRecipeSearch(searchKey: string, pageNumber: number) {
    return this.httpClient.get<RecipeDto[]>(BASE_URL + "search?search_key=" + searchKey + "&page_number=" + pageNumber);
  }

  getRecipeDetails(name: string) {
    return this.httpClient.get<RecipeDetailsDto>(BASE_URL + "details/" + name);
  }

  getRecipeAuthor(author: string, pageNumber: number) {
    return this.httpClient.get<RecipeDto[]>(BASE_URL + "author?page_number=" + pageNumber + "&author=" + author);
  }

  getRecipeFilter(filter: string, pageNumber: number) {
    return this.httpClient.get<RecipeDto[]>(BASE_URL + "filter?page_number=" + pageNumber + "&ingredients=" + filter);
  }

  getRecipeNumberOfPages() {
    return this.httpClient.get<number>(BASE_URL + "number_of_pages");
  }

  getRecipeNumberOfPagesSearch(searchKey: string) {
    return this.httpClient.get<number>(BASE_URL + "number_of_pages/search?search_key=" + searchKey);
  }

  getRecipeNumberOfPagesAuthor(author: string) {
    return this.httpClient.get<number>(BASE_URL + "number_of_pages/author?author=" + author);
  }

  getRecipeNumberOfPagesFilter(ingredients: string) {
    return this.httpClient.get<number>(BASE_URL + "number_of_pages/filter?ingredients=" + ingredients);
  }
}
