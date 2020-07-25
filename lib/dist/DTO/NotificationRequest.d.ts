export declare class NotificationRequest {
    identifier?: string;
    payload: any;
    constructor(payload: object);
    readonly title: string;
    readonly category: string;
    readonly fireDate: string;
    readonly sound: string;
    readonly body: string;
}
