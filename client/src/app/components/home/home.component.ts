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

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatHeaderCellDef, MatHeaderCell, MatCellDef, MatHeaderRowDef, MatRowDef, MatHeaderRow, MatRow, MatCell,
    MatColumnDef, MatPaginator, MatButton, MatIcon, MatIconButton, MatTable],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {
  private recipeService = inject(RecipeService);
  recipes: RecipeDto[] = [];
  displayedColumns: string[] = ['recipe-name', 'recipe-author', 'recipe-number-of-ingredients', 'recipe-skill-level'];
  pageNumber = 1;
  numberPages = 0;

  constructor(private recipeDialog: MatDialog) {
  }

  ngOnInit(): void {
    this.fetchRecipes();
    this.getNumberPages();
  }

  fetchPage(pageNumber: number) {
    if (pageNumber > 0 && pageNumber <= this.numberPages) {
      this.pageNumber = pageNumber;
      this.fetchRecipes();
    }
  }

  private fetchRecipes() {
    this.recipeService.getRecipe(this.pageNumber).subscribe({
      next: recipes => {
        this.recipes = recipes;
      }, error: err => {
        console.log(err);
      }
    })
  }

  private getNumberPages() {
    this.recipeService.getNumberPages().subscribe({
      next: value => {
        this.numberPages = value;
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
      },
      error: err => {
        console.log(err);
      }
    })
  }
}
