import {NgModule} from '@angular/core';

import {MenuItems} from './menu-items/menu-items';
import {AccordionAnchorDirective, AccordionDirective, AccordionLinkDirective} from './accordion';
import {ResourceService} from '../core/services/util-services/resource.service';
import { ListFilterPipe } from '../commons/pipes/list-filter.pipe';

@NgModule({
  declarations: [
    AccordionAnchorDirective,
    AccordionLinkDirective,
    AccordionDirective,
    ListFilterPipe
  ],
  exports: [
    AccordionAnchorDirective,
    AccordionLinkDirective,
    AccordionDirective,
    ListFilterPipe
  ],
  providers: [MenuItems, ResourceService]
})
export class SharedModule {}
