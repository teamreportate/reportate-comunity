import {Pipe, PipeTransform} from '@angular/core';

@Pipe({name: 'deleteUnderline'})
export class DeleteUnderline implements PipeTransform {
  transform(value: string): string {
    if(value==null){
      return "";
    }else{
      return value.replace(/_/g, ' ');
    }

  }
}
