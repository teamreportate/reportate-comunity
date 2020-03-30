/**
 * MC4
 * Santa Cruz - Bolivia
 * project:
 * package:
 * date:    26-02-19
 * author:  fmontero
 **/
import {Injectable} from '@angular/core';

export class Status {
  code: number;
  statusText: string;
  constructor(code: number, statuxText: string) {
    this.code = code;
    this.statusText = statuxText;
  }
}

@Injectable()
export class HttpStatus {
  public static CONTINUE: Status = new Status(100, 'Continue');
  public static SWITCHING_PROTOCOLS: Status = new Status(101, 'Switching Protocols');
  public static PROCESSING: Status = new Status(102, 'Processing');
  public static CHECKPOINT: Status = new Status(103, 'Checkpoint');
  public static OK: Status = new Status(200, 'OK');
  public static CREATED: Status = new Status(201, 'Created');
  public static ACCEPTED: Status = new Status(202, 'Accepted');
  public static NON_AUTHORITATIVE_INFORMATION: Status = new Status(203, 'Non-Authoritative Information');
  public static NO_CONTENT: Status = new Status(204, 'No Content');
  public static RESET_CONTENT: Status = new Status(205, 'Reset Content');
  public static PARTIAL_CONTENT: Status = new Status(206, 'Partial Content');
  public static MULTI_STATUS: Status = new Status(207, 'Multi-Status');
  public static ALREADY_REPORTED: Status = new Status(208, 'Already Reported');
  public static IM_USED: Status = new Status(226, 'IM Used');
  public static MULTIPLE_CHOICES: Status = new Status(300, 'Multiple Choices');
  public static MOVED_PERMANENTLY: Status = new Status(301, 'Moved Permanently');
  public static FOUND: Status = new Status(302, 'Found');
  public static MOVED_TEMPORARILY: Status = new Status(302, 'Moved Temporarily');
  public static SEE_OTHER: Status = new Status(303, 'See Other');
  public static NOT_MODIFIED: Status = new Status(304, 'Not Modified');
  public static USE_PROXY: Status = new Status(305, 'Use Proxy');
  public static TEMPORARY_REDIRECT: Status = new Status(307, 'Temporary Redirect');
  public static PERMANENT_REDIRECT: Status = new Status(308, 'Permanent Redirect');
  public static BAD_REQUEST: Status = new Status(400, 'Bad Request');
  public static UNAUTHORIZED: Status = new Status(401, 'Unauthorized');
  public static PAYMENT_REQUIRED: Status = new Status(402, 'Payment Required');
  public static FORBIDDEN: Status = new Status(403, 'Forbidden');
  public static NOT_FOUND: Status = new Status(404, 'Not Found');
  public static METHOD_NOT_ALLOWED: Status = new Status(405, 'Method Not Allowed');
  public static NOT_ACCEPTABLE: Status = new Status(406, 'Not Acceptable');
  public static PROXY_AUTHENTICATION_REQUIRED: Status = new Status(407, 'Proxy Authentication Required');
  public static REQUEST_TIMEOUT: Status = new Status(408, 'Request Timeout');
  public static CONFLICT: Status = new Status(409, 'Conflict');
  public static GONE: Status = new Status(410, 'Gone');
  public static LENGTH_REQUIRED: Status = new Status(411, 'Length Required');
  public static PRECONDITION_FAILED: Status = new Status(412, 'Precondition Failed');
  public static PAYLOAD_TOO_LARGE: Status = new Status(413, 'Payload Too Large');
  public static REQUEST_ENTITY_TOO_LARGE: Status = new Status(413, 'Request Entity Too Large');
  public static URI_TOO_LONG: Status = new Status(414, 'URI Too Long');
  public static REQUEST_URI_TOO_LONG: Status = new Status(414, 'Request-URI Too Long');
  public static UNSUPPORTED_MEDIA_TYPE: Status = new Status(415, 'Unsupported Media Type');
  public static REQUESTED_RANGE_NOT_SATISFIABLE: Status = new Status(416, 'Requested range not satisfiable');
  public static EXPECTATION_FAILED: Status = new Status(417, 'Expectation Failed');
  public static I_AM_A_TEAPOT: Status = new Status(418, 'Im a teapot');
  public static INSUFFICIENT_SPACE_ON_RESOURCE: Status = new Status(419, 'Insufficient Space On Resource');
  public static METHOD_FAILURE: Status = new Status(420, 'Method Failure');
  public static DESTINATION_LOCKED: Status = new Status(421, 'Destination Locked');
  public static UNPROCESSABLE_ENTITY: Status = new Status(422, 'Unprocessable Entity');
  public static LOCKED: Status = new Status(423, 'Locked');
  public static FAILED_DEPENDENCY: Status = new Status(424, 'Failed Dependency');
  public static UPGRADE_REQUIRED: Status = new Status(426, 'Upgrade Required');
  public static PRECONDITION_REQUIRED: Status = new Status(428, 'Precondition Required');
  public static TOO_MANY_REQUESTS: Status = new Status(429, 'Too Many Requests');
  public static REQUEST_HEADER_FIELDS_TOO_LARGE: Status = new Status(431, 'Request Header Fields Too Large');
  public static UNAVAILABLE_FOR_LEGAL_REASONS: Status = new Status(451, 'Unavailable For Legal Reasons');
  public static INTERNAL_SERVER_ERROR: Status = new Status(500, 'Internal Server Error');
  public static NOT_IMPLEMENTED: Status = new Status(501, 'Not Implemented');
  public static BAD_GATEWAY: Status = new Status(502, 'Bad Gateway');
  public static SERVICE_UNAVAILABLE: Status = new Status(503, 'Service Unavailable');
  public static GATEWAY_TIMEOUT: Status = new Status(504, 'Gateway Timeout');
  public static HTTP_VERSION_NOT_SUPPORTED: Status = new Status(505, 'HTTP Version not supported');
  public static VARIANT_ALSO_NEGOTIATES: Status = new Status(506, 'Variant Also Negotiates');
  public static INSUFFICIENT_STORAGE: Status = new Status(507, 'Insufficient Storage');
  public static LOOP_DETECTED: Status = new Status(508, 'Loop Detected');
  public static BANDWIDTH_LIMIT_EXCEEDED: Status = new Status(509, 'Bandwidth Limit Exceeded');
  public static NOT_EXTENDED: Status = new Status(510, 'Not Extended');
  public static NETWORK_AUTHENTICATION_REQUIRED: Status = new Status(511, 'Network Authentication Required');
  private okStatus: Status[] = [];
  private serverErrorStatus: Status[] = [];
  private clientErrorStatus: Status[] = [];

  constructor() {
    this.okStatus.push(HttpStatus.OK);
    this.okStatus.push(HttpStatus.CREATED);
    this.okStatus.push(HttpStatus.ACCEPTED);
    this.okStatus.push(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
    this.okStatus.push(HttpStatus.NO_CONTENT);
    this.okStatus.push(HttpStatus.RESET_CONTENT);
    this.okStatus.push(HttpStatus.PARTIAL_CONTENT);
    this.okStatus.push(HttpStatus.MULTI_STATUS);
    this.okStatus.push(HttpStatus.ALREADY_REPORTED);
    this.okStatus.push(HttpStatus.IM_USED);

    this.serverErrorStatus.push(HttpStatus.INTERNAL_SERVER_ERROR);
    this.serverErrorStatus.push(HttpStatus.NOT_IMPLEMENTED);
    this.serverErrorStatus.push(HttpStatus.BAD_GATEWAY);
    this.serverErrorStatus.push(HttpStatus.SERVICE_UNAVAILABLE);
    this.serverErrorStatus.push(HttpStatus.GATEWAY_TIMEOUT);
    this.serverErrorStatus.push(HttpStatus.HTTP_VERSION_NOT_SUPPORTED);
    this.serverErrorStatus.push(HttpStatus.VARIANT_ALSO_NEGOTIATES);
    this.serverErrorStatus.push(HttpStatus.INSUFFICIENT_STORAGE);
    this.serverErrorStatus.push(HttpStatus.LOOP_DETECTED);
    this.serverErrorStatus.push(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
    this.serverErrorStatus.push(HttpStatus.NOT_EXTENDED);
    this.serverErrorStatus.push(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);

    this.clientErrorStatus.push(HttpStatus.BAD_REQUEST);
    this.clientErrorStatus.push(HttpStatus.UNAUTHORIZED);
    this.clientErrorStatus.push(HttpStatus.PAYMENT_REQUIRED);
    this.clientErrorStatus.push(HttpStatus.FORBIDDEN);
    this.clientErrorStatus.push(HttpStatus.NOT_FOUND);
    this.clientErrorStatus.push(HttpStatus.METHOD_NOT_ALLOWED);
    this.clientErrorStatus.push(HttpStatus.NOT_ACCEPTABLE);
    this.clientErrorStatus.push(HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
    this.clientErrorStatus.push(HttpStatus.REQUEST_TIMEOUT);
    this.clientErrorStatus.push(HttpStatus.CONFLICT);
    this.clientErrorStatus.push(HttpStatus.GONE);
    this.clientErrorStatus.push(HttpStatus.LENGTH_REQUIRED);
    this.clientErrorStatus.push(HttpStatus.PRECONDITION_FAILED);
    this.clientErrorStatus.push(HttpStatus.PAYLOAD_TOO_LARGE);
    this.clientErrorStatus.push(HttpStatus.REQUEST_ENTITY_TOO_LARGE);
    this.clientErrorStatus.push(HttpStatus.URI_TOO_LONG);
    this.clientErrorStatus.push(HttpStatus.REQUEST_URI_TOO_LONG);
    this.clientErrorStatus.push(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    this.clientErrorStatus.push(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
    this.clientErrorStatus.push(HttpStatus.EXPECTATION_FAILED);
    this.clientErrorStatus.push(HttpStatus.I_AM_A_TEAPOT);
    this.clientErrorStatus.push(HttpStatus.INSUFFICIENT_SPACE_ON_RESOURCE);
    this.clientErrorStatus.push(HttpStatus.METHOD_FAILURE);
    this.clientErrorStatus.push(HttpStatus.DESTINATION_LOCKED);
    this.clientErrorStatus.push(HttpStatus.UNPROCESSABLE_ENTITY);
    this.clientErrorStatus.push(HttpStatus.LOCKED);
    this.clientErrorStatus.push(HttpStatus.FAILED_DEPENDENCY);
    this.clientErrorStatus.push(HttpStatus.UPGRADE_REQUIRED);
    this.clientErrorStatus.push(HttpStatus.PRECONDITION_REQUIRED);
    this.clientErrorStatus.push(HttpStatus.TOO_MANY_REQUESTS);
    this.clientErrorStatus.push(HttpStatus.REQUEST_HEADER_FIELDS_TOO_LARGE);
    this.clientErrorStatus.push(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);

  }

  statusFromCodeStatus(code: number):Status {
    let status:Status = this.okStatus.filter(item=> item.code === code)[0];
    if (status) return status;

    status = this.clientErrorStatus.filter(item=> item.code === code)[0];
    if (status) return status;

    status = this.serverErrorStatus.filter(item=> item.code === code)[0];
    if (status) return status;

    return new Status(code, 'Error: Por favor verifique su conexion a la red');
  }
}

