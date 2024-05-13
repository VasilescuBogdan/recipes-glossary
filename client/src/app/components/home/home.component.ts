import {Component, inject, OnInit} from '@angular/core';
import {RecipeDto} from "../../dto/recipe.dto";
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable,
} from "@angular/material/table";
import {RecipeService} from "../../service/recipe.service";
import {MatPaginator} from "@angular/material/paginator";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {MatDialog} from "@angular/material/dialog";
import {RecipeDetailsComponent} from "../recipe-details/recipe-details.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {ActivatedRoute, Router} from "@angular/router";
import {NgIf} from "@angular/common";
import {MatOption, MatSelect} from "@angular/material/select";
import {IngredientService} from "../../service/ingredient.service";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatHeaderCellDef, MatHeaderCell, MatCellDef, MatHeaderRowDef, MatRowDef, MatHeaderRow, MatRow, MatCell,
    MatColumnDef, MatPaginator, MatButton, MatIcon, MatIconButton, MatTable, FormsModule, MatLabel, MatFormField,
    MatInput, NgIf, MatSelect, ReactiveFormsModule, MatOption],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  private recipeService = inject(RecipeService);
  private ingredientService = inject(IngredientService);
  recipes: RecipeDto[] = [];
  displayedColumns: string[] = ['recipe-name', 'recipe-author', 'recipe-number-of-ingredients', 'recipe-skill-level'];
  pageNumber = 1;
  numberOfPages = 0;
  searchKey = '';
  searchBox = '';
  author = '';
  ingredients: string[] = [];
  filterItems: string = '';
  selectOptions: string[] = [];

  constructor(private recipeDialog: MatDialog, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.searchKey = params['search'] || '';
      this.author = params['author'] || '';
      this.filterItems = params['filter'] || [];
    })
    if (this.searchKey === '' && this.author === '' && this.filterItems.length === 0) {
      this.fetchRecipes();
    }
    if (this.searchKey !== '') {
      this.fetchRecipesSearch();
    }
    if (this.author !== '') {
      this.fetchAuthorSearch();
    }
    if (this.filterItems.length !== 0) {
      this.fetchFilterSearch();
    }
    this.fetchIngredients();
  }

  fetchPage(pageNumber: number) {
    this.pageNumber = pageNumber;
    if (this.searchKey !== '') {
      this.recipeService.getRecipeSearch(this.searchKey, pageNumber).subscribe({
        next: recipes => {
          this.recipes = recipes;
        }, error: err => {
          console.log(err);
        }
      })
    }
    if (this.author !== '') {
      this.recipeService.getRecipeAuthor(this.author, pageNumber).subscribe({
        next: recipes => {
          this.recipes = recipes;
        }, error: err => {
          console.log(err);
        }
      })
    }
    if (this.filterItems.length !== 0) {
      this.recipeService.getRecipeFilter(this.filterItems, pageNumber).subscribe( {
        next: recipes => {
          this.recipes = recipes;
        }, error: err => {
          console.log(err);
        }
      })
    }
    if (this.searchKey === '' && this.author === '' && this.filterItems.length === 0) {
      this.recipeService.getRecipe(pageNumber).subscribe({
        next: recipes => {
          this.recipes = recipes;
        }, error: err => {
          console.log(err);
        }
      })
    }
  }

  private fetchRecipes() {
    this.recipeService.getRecipeNumberOfPages().subscribe({
      next: numberOfPages => {
        this.numberOfPages = numberOfPages;
      }, error: err => {
        console.log(err);
      }
    })
    this.recipeService.getRecipe(this.pageNumber).subscribe({
      next: recipes => {
        this.recipes = recipes;
      }, error: err => {
        console.log(err);
      }
    })
  }

  openRecipeDialog(name: string) {
    this.recipeService.getRecipeDetails(name).subscribe({
      next: value => {
        this.recipeDialog.open(RecipeDetailsComponent, {
          data: {value, name}, width: '900px', height: '600px'
        })
      }, error: err => {
        console.log(err);
      }
    })
  }

  search() {
    this.router.navigate(['/search/' + this.searchBox]).then(r => console.log(r));
  }

  filterIngredients() {
    this.router.navigate(['/filter/' + this.selectOptions]).then(r => console.log(r));
  }

  private fetchRecipesSearch() {
    this.recipeService.getRecipeNumberOfPagesSearch(this.searchKey).subscribe({
      next: numberOfPages => {
        this.numberOfPages = numberOfPages;
      }, error: err => {
        console.log(err);
      }
    })
    this.recipeService.getRecipeSearch(this.searchKey, this.pageNumber).subscribe({
      next: recipes => {
        this.recipes = recipes;
      }, error: err => {
        console.log(err);
      }
    })
  }

  private fetchAuthorSearch() {
    this.recipeService.getRecipeNumberOfPagesAuthor(this.author).subscribe({
      next: numberOfPages => {
        this.numberOfPages = numberOfPages;
      }, error: err => {
        console.log(err);
      }
    })
    this.recipeService.getRecipeAuthor(this.author, this.pageNumber).subscribe({
      next: recipes => {
        this.recipes = recipes;
      }, error: err => {
        console.log(err);
      }
    })
  }

  getAuthorRecipe(author: string) {
    this.router.navigate(['/author/' + author]).then(r => console.log(r));
  }

  private fetchFilterSearch() {
    this.selectOptions = this.filterItems.split(",");
    this.recipeService.getRecipeNumberOfPagesFilter(this.filterItems).subscribe({
      next: numberOfPages => {
        this.numberOfPages = numberOfPages;
      }, error: err => {
        console.log(err);
      }
    })
    this.recipeService.getRecipeFilter(this.filterItems, this.pageNumber).subscribe( {
      next: recipes => {
        this.recipes = recipes;
      }, error: err => {
        console.log(err);
      }
    })
  }

  private fetchIngredients() {
    this.ingredientService.getIngredients().subscribe({
      next: ingredients => {
        this.ingredients = ingredients;
      }, error: err => {
        console.log(err);
      }
    })
  }
}
