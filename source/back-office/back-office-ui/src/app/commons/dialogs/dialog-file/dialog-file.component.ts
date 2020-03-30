import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {InvoiceFile} from '../../../core/models/invoice-file';
import {InvoiceDocumentService} from '../../../core/services/http-services/invoice-document.service';

@Component({
  selector: 'app-dialog-file1',
  templateUrl: './dialog-file.component.html',
  styleUrls: ['./dialog-file.component.sass']
})
export class DialogFileComponent implements OnInit {

  load: boolean = false;
  pageNumber: number = 1;
  obj = { data: '' };

  public invoiceId: number;
  public invoiceFile: InvoiceFile;
  constructor( private invoiceDocumentService: InvoiceDocumentService,
               public dialogRef: MatDialogRef<DialogFileComponent>, @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit() {
    this.invoiceFile = this.data.invoiceFile;
    this.obj.data = atob(this.invoiceFile.archivoBase64);
  }

  onCloseFileDialog() {
    this.dialogRef.close();
  }

  printFile() {
    let textEncoded: string = atob(this.invoiceFile.archivoBase64);
    let length = textEncoded.length;
    let uintArray = new Uint8Array(new ArrayBuffer(length));

    for (let i = 0; i < length; i++) {
      uintArray[i] = textEncoded.charCodeAt(i);
    }

    let currentBlob = new Blob([uintArray], {type: 'application/pdf'});

    const iframe = document.createElement('iframe');
    iframe.style.display = 'none';
    iframe.src = URL.createObjectURL(currentBlob);
    document.body.appendChild(iframe);
    iframe.contentWindow.print();

  }

  previousPdfPage() {
    this.pageNumber>1? this.pageNumber-- : this.pageNumber;
  }

  nextPdfPage() {
    this.pageNumber ++;
  }


}
