"use client";

import { ApolloProvider } from "@apollo/client";
import client from "../../lib/apolloClient";

const host = process.env.NEXT_PUBLIC_API_HOST;

export function ApolloWrapper({ children }: { children: React.ReactNode }) {
  return <ApolloProvider client={client}>{children}</ApolloProvider>;
}
