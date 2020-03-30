/**
 * MC4 SRL
 * Santa Cruz - Bolivia
 * project: back-office-ui
 * date:    13-09-19
 * author:  fmontero
 **/

import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'xmlPretty'
})

export class XmlPrettyPipe implements PipeTransform {
  transform(xml: any, indent: number): any {
    if (isFinite(indent)) {
      if (indent !== 0) {
        indent = indent || 2;
      }
    } else if (typeof indent !== 'string') {
      indent = 2;
    }

    const arr = xml.replace(/>\s*</gm, '><')
      .replace(/</g, '~::~<')
      .replace(/\s*xmlns([=:])/g, '~::~xmlns$1')
      .split('~::~');

    let len = arr.length,
      inComment = false,
      depth = 0,
      string = '',
      shift = XmlPrettyPipe.createShiftArr(indent);

    for (let i = 0; i < len; i++) {
      if (arr[i].indexOf('<!') !== -1) {
        string += shift[depth] + arr[i];
        inComment = true;

        if (arr[i].indexOf('-->') !== -1 || arr[i].indexOf(']>') !== -1 ||
          arr[i].indexOf('!DOCTYPE') !== -1) {
          inComment = false;
        }
      } else if (arr[i].indexOf('-->') !== -1 || arr[i].indexOf(']>') !== -1) {
        string += arr[i];
        inComment = false;
      } else if (/^<\w/.test(arr[i - 1]) && /^<\/\w/.test(arr[i]) &&
        String(/^<[\w:\-\.,]+/.exec(arr[i - 1])) == /^<\/[\w:\-\.,]+/.exec(arr[i])[0].replace('/', '')) { // fixme WTF?
        string += arr[i];
        if (!inComment) {
          depth--;
        }
      } else if (arr[i].search(/<\w/) !== -1 && arr[i].indexOf('</') === -1 && arr[i].indexOf('/>') === -1) { // <elm> //
        string += !inComment ? (shift[depth++] + arr[i]) : arr[i];
      } else if (arr[i].search(/<\w/) !== -1 && arr[i].indexOf('</') !== -1) { // <elm>...</elm> //
        string += !inComment ? shift[depth] + arr[i] : arr[i];
      } else if (arr[i].search(/<\//) > -1) { // </elm> //
        string += !inComment ? shift[--depth] + arr[i] : arr[i];
      } else if (arr[i].indexOf('/>') !== -1) { // <elm/> //
        string += !inComment ? shift[depth] + arr[i] : arr[i];
      } else if (arr[i].indexOf('<?') !== -1) { // <? xml ... ?> //
        string += shift[depth] + arr[i];
      } else if (arr[i].indexOf('xmlns:') !== -1 || arr[i].indexOf('xmlns=') !== -1) { // xmlns //
        string += shift[depth] + arr[i];
      } else {
        string += arr[i];
      }
    }

    return string.trim();
  }


  static createShiftArr(step: any) {
    let space = '';
    if (isNaN(parseInt(step))) { // argument is string
      space = step;
    } else { // argument is integer
      for (let i = 0; i < step; i++) {
        space += ' ';
      }
    }

    let shift = ['\n']; // array of shifts

    for (let ix = 0; ix < 100; ix++) {
      shift.push(shift[ix] + space);
    }

    return shift;
  }

}
