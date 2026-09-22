import { NextResponse } from "next/server";
import network from "@/config/network.json";

export function GET() {
  return NextResponse.json(network);
}
